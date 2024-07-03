package com.example.mypet.security.service.custom;

import com.example.mypet.security.domain.oauth.OAuth2AuthorizedClientEntity;
import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import com.example.mypet.security.repository.OAuth2AuthorizedClientRepository;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.security.service.oauthInfoSerivce.OAuth2UserInfoServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final UsersRepository usersRepository;
    private final OAuth2UserInfoServiceProvider userInfoServiceProvider;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = userInfoServiceProvider.getOAuth2UserInfoService(provider).loadUserInfo(oAuth2User);

        Users user = processUserRegistration(oAuth2UserInfo, provider);

        String accessToken = jwtTokenStrategy.generateAccessToken(user.getEmail());
        String refreshToken = jwtTokenStrategy.generateRefreshToken(user.getEmail());

        // Save OAuth2 client information
        OAuth2AuthorizedClientEntity clientEntity = OAuth2AuthorizedClientEntity.builder()
                .clientRegistrationId(provider)
                .principalName(user.getEmail())
                .accessToken(new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, null, null)) // Set appropriate dates
                .refreshToken(new OAuth2RefreshToken(refreshToken, null)) // Set appropriate dates
                .build();
        authorizedClientRepository.save(clientEntity);

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("access_token", accessToken);
        attributes.put("refresh_token", refreshToken); // 클라이언트에 Refresh Token을 전달할 필요가 있을 때 사용

        Set<OAuth2UserAuthority> authorities = new HashSet<>();
        authorities.add(new OAuth2UserAuthority(attributes));

        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(authorities, attributes, "email");

        // SecurityContext에 사용자 저장
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                defaultOAuth2User, null, defaultOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return defaultOAuth2User;
    }

    private Users processUserRegistration(OAuth2UserInfo oAuth2UserInfo, String provider) {
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        Users user = optionalUser.orElseGet(() -> Users.createUser(email, name, provider));
        if (optionalUser.isEmpty()) {
            usersRepository.save(user);
        }
        return user;
    }
}
