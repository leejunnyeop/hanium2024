package com.example.mypet.controller;

import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import com.example.mypet.security.domain.refresh.RefreshTokenRequest;
import com.example.mypet.security.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JwtController {


    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<OAuth2AccessTokenResponse> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
        OAuth2AccessTokenResponse response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(response);
    }
    @GetMapping("/success")
    public String home(){
        return "home";
    }


}
