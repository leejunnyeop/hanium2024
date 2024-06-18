package com.example.mypet.security.service;

import com.example.mypet.security.domain.NaverUserInfo;
import com.example.mypet.security.domain.OAuth2UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NaverOAuth2UserService implements OAuth2UserInfoService {

    @Override
    public boolean supports(String provider) {
        return "naver".equals(provider);
    }

    @Override
    public OAuth2UserInfo loadUserInfo(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        return new NaverUserInfo(attributes);
    }

}
