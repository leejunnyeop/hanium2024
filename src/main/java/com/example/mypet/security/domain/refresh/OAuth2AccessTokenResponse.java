package com.example.mypet.security.domain.refresh;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OAuth2AccessTokenResponse {

    private String accessToken;
    private String refreshToken;
}
