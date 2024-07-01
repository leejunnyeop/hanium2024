package com.example.mypet.security.controller;

import com.example.mypet.security.domain.refresh.AuthResponse;
import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import com.example.mypet.security.domain.refresh.RefreshTokenRequest;
import com.example.mypet.security.domain.refresh.SocialAuthRequest;
import com.example.mypet.security.service.tokenService.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "social", description = "소셜 로그인 관련 API")
@Log4j2
public class SocialAuthController {

    private final AuthService authService;


    @Operation(summary = "소셜 로그인", description = "",
            responses = {@ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema())))})
    @PostMapping("/social/callback")
    public ResponseEntity<AuthResponse> handleOAuth2Callback(@RequestBody SocialAuthRequest request) {
        log.info("/social/callback 실행중");
        AuthResponse response = authService.handleOAuth2Callback(request.getProvider(), request.getCode(), request.getCodeVerifier());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "JWT refresh", description = "Access Token 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<OAuth2AccessTokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        OAuth2AccessTokenResponse response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }

}
