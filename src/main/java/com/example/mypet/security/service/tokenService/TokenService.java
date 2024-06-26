package com.example.mypet.security.service.tokenService;

import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;

public interface TokenService {

    OAuth2AccessTokenResponse getAccessTokenResponse(String provider, String code, String codeVerifier);

}
