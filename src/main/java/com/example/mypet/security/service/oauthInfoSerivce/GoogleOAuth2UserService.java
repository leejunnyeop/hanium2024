package com.example.mypet.security.service.oauthInfoSerivce;

import com.example.mypet.security.domain.oauth.GoogleUserInfo;
import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GoogleOAuth2UserService implements OAuth2UserInfoService {


    @Override
    public boolean supports(String provider) {
        return "google".equals(provider);
    }

    @Override
    public OAuth2UserInfo loadUserInfo(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return new GoogleUserInfo(attributes);
    }
}
