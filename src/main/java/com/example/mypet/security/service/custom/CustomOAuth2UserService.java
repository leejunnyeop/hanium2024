package com.example.mypet.security.service.custom;

import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import com.example.mypet.security.jwt.refresh.RefreshTokenStoreStrategy;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.security.service.oauthSerivce.OAuth2UserInfoServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final RefreshTokenStoreStrategy refreshTokenStoreStrategy;
    private final UsersRepository usersRepository;
    private final OAuth2UserInfoServiceProvider userInfoServiceProvider;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = userInfoServiceProvider.getOAuth2UserInfoService(provider).loadUserInfo(oAuth2User);

        Users users = processUserRegistration(oAuth2UserInfo, provider);

        String accessToken = jwtTokenStrategy.generateAccessToken(users.getEmail());
        String refreshToken = jwtTokenStrategy.generateRefreshToken(users.getEmail());

        refreshTokenStoreStrategy.saveRefreshToken(users.getEmail(), refreshToken);

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("access_token", accessToken);
        attributes.put("refresh_token", refreshToken); // 클라이언트에 Refresh Token을 전달할 필요가 있을 때 사용

        Set<OAuth2UserAuthority> authorities = new HashSet<>();
        authorities.add(new OAuth2UserAuthority(attributes));

        return new DefaultOAuth2User(authorities, attributes, "email");
    }

    private Users processUserRegistration(OAuth2UserInfo oAuth2UserInfo, String provider) {
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        Users users = usersRepository.findByEmail(email);
        if (users == null) {
            users = Users.createUser(email, name, provider);
            usersRepository.save(users);
        }
        return users;
    }
}
