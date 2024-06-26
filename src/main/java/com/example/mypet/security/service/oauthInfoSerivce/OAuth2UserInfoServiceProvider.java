package com.example.mypet.security.service.oauthSerivce;

import com.example.mypet.security.service.oauthSerivce.OAuth2UserInfoService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OAuth2UserInfoServiceProvider {

    private final List<OAuth2UserInfoService> userInfoServices;

    public OAuth2UserInfoServiceProvider(List<OAuth2UserInfoService> userInfoServices) {
        this.userInfoServices = userInfoServices;
    }

    public OAuth2UserInfoService getOAuth2UserInfoService(String provider) {
        return userInfoServices.stream()
                .filter(service -> service.supports(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth2 제공자입니다: " + provider));
    }
}
