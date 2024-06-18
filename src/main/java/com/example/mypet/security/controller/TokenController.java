package com.example.mypet.security.controller;

import com.example.mypet.security.domain.refresh.TokenRefreshRequest;
import com.example.mypet.security.domain.refresh.TokenResponse;
import com.example.mypet.security.ex.CustomAuthenticationException;
import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import com.example.mypet.security.jwt.refresh.RefreshTokenStoreStrategy;
import com.example.mypet.security.service.custom.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final CustomUserDetailsService customUserDetailsService;
    private final RefreshTokenStoreStrategy refreshTokenStoreStrategy;

    @GetMapping("/generate")
    public ResponseEntity<TokenResponse> generateToken(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new CustomAuthenticationException("인증되지 않은 사용자입니다.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenStrategy.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtTokenStrategy.generateRefreshToken(userDetails.getUsername());
        refreshTokenStoreStrategy.saveRefreshToken(userDetails.getUsername(), refreshToken);
        TokenResponse response = new TokenResponse(accessToken, refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestBody TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();
        String email = jwtTokenStrategy.extractUsername(refreshToken);
        if (refreshTokenStoreStrategy.getRefreshToken(email).equals(refreshToken) && jwtTokenStrategy.validateToken(refreshToken, email)) {
            String newAccessToken = jwtTokenStrategy.generateAccessToken(email);
            TokenResponse response = new TokenResponse(newAccessToken, refreshToken);
            return ResponseEntity.ok(response);
        } else {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
    }
}
