package com.example.mypet.security.controller;

import com.example.mypet.security.domain.refresh.AuthResponse;
import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import com.example.mypet.security.domain.refresh.RefreshTokenRequest;
import com.example.mypet.security.domain.refresh.SocialAuthRequest;
import com.example.mypet.security.service.tokenService.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/social")
@RequiredArgsConstructor
@Log4j2
public class SocialAuthController {

    private final AuthService authService;


    @PostMapping("/social/callback")
    public ResponseEntity<AuthResponse> handleOAuth2Callback(@RequestBody SocialAuthRequest request) {
        log.info("/social/callback 실행중");
        AuthResponse response = authService.handleOAuth2Callback(request.getProvider(), request.getCode(), request.getCodeVerifier());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<OAuth2AccessTokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        OAuth2AccessTokenResponse response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

}
