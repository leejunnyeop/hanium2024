package com.example.mypet.security.domain;

import lombok.Getter;

@Getter
public class TokenRefreshRequest {

    private String refreshToken;
}
