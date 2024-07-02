package com.example.mypet.security.domain.users;

import com.nimbusds.jwt.JWT;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

/**
 * MongoDB에 저장되는 사용자 정보를 담는 엔티티 클래스.
 */
@Builder
@Getter
@Document(collection = "users")
public class Users {

    @Id
    private String id;
    private String email;
    private String name;
    private String provider;
    private String jwtRefreshToken; // JWT 리프레시 토큰

    private OAuth2AccessToken accessToken; // OAuth2 액세스 토큰
    private OAuth2RefreshToken refreshToken; // OAuth2 리프레시 토큰
    private String clientRegistrationId; // 클라이언트 등록 ID (예: google, naver)

    /**
     * 새로운 사용자 생성 메서드
     */
    public static Users createUser(String email, String name, String provider, OAuth2AccessToken accessToken, String clientRegistrationId) {
        return Users.builder()
                .email(email)
                .name(name)
                .provider(provider)
                .accessToken(accessToken)
                .clientRegistrationId(clientRegistrationId)
                .build();
    }

    /**
     * OAuth2 토큰 갱신 메서드
     */
    public void updateTokens(OAuth2AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    /**
            * JWT 리프레시 토큰 갱신 메서드
     */
    public void updateJwtRefreshToken(String jwtRefreshToken) {
        this.jwtRefreshToken = jwtRefreshToken;
    }
}
