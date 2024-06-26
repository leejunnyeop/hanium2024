package com.example.mypet.security.service.oauthInfoSerivce;

import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface OAuth2UserInfoService {

    boolean supports(String provider);
    OAuth2UserInfo loadUserInfo(OAuth2User oAuth2User);
}
