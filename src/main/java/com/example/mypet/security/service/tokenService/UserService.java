package com.example.mypet.security.service.tokenService;

import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.users.UsersDto;

public interface UserService {

    UsersDto processUser(OAuth2UserInfo userInfo, String provider);
    OAuth2UserInfo getOAuth2UserInfo(String provider, String accessToken);
}
