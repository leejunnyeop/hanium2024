package com.example.mypet.security.service.custom;

import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.security.service.oauthInfoSerivce.OAuth2UserInfoServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsersRepository usersRepository;
    private final OAuth2UserInfoServiceProvider userInfoServiceProvider;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserInfo oAuth2UserInfo = userInfoServiceProvider.getOAuth2UserInfoService(provider).loadUserInfo(oAuth2User);

        Users user = processUserRegistration(oAuth2UserInfo, provider);

        //  사용자 정보를 포함한 OAuth2User 반환
        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                oAuth2User.getAttributes(),
                "email"
        );

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
