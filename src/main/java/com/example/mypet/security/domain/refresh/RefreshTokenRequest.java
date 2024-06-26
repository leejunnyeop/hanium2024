package com.example.mypet.security.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenRequest {

    private String accessToken;
    private String refreshToken;
}
