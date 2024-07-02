package com.example.mypet.security.service.authService;


import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;

import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import com.example.mypet.security.service.oauthInfoSerivce.MongoOAuth2AuthorizedClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final MongoOAuth2AuthorizedClientService authorizedClientService;



    public OAuth2AccessTokenResponse refreshAccessToken(String refreshToken) {

        // Refresh Token 유효성 검사 및 사용자 정보 조회
        String email = jwtTokenStrategy.extractUsername(refreshToken);

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient("google", email);
        if (authorizedClient == null || !jwtTokenStrategy.validateToken(refreshToken, email)) {
            authorizedClient = authorizedClientService.loadAuthorizedClient("naver", email);
            if (authorizedClient == null || !jwtTokenStrategy.validateToken(refreshToken, email)) {
                throw new IllegalArgumentException("잘못된 리프레시 토큰입니다.");
            }
        }

        // 새로운 Access Token 생성
        String newAccessToken = jwtTokenStrategy.generateAccessToken(email);

        // 새로운 Access Token을 포함한 응답 반환
        return new OAuth2AccessTokenResponse(newAccessToken, refreshToken);
    }
}
