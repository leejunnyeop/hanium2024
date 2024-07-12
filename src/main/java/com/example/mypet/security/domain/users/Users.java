package com.example.mypet.security.domain.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

/**
 * MongoDB에 저장되는 사용자 정보를 담는 엔티티 클래스.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Document(collection = "users")
public class Users {

    @Id
    @Schema(description = "id", example = "6691113b6f3f5a4936fe4ec3")
    private String id;
    @Schema(description = "이메일", example = "alex96320@gmail.com")
    private String email;
    @Schema(description = "이름", example = "홍창현")
    private String name;
    @Schema(description = "provider", example = "google")
    private String provider;
    @Schema(description = "역할", example = "user")
    private String role;
    @Schema(description = "refreshToken", example = "user")
    private String jwtRefreshToken; // JWT 리프레시 토큰

    // 전자지갑
    @Schema(description = "전자지갑 주소" )
    private String address;
    @Schema(description = "publicKey" )
    private String publicKey;
    @Schema(description = "암호화 된 전자지갑 privateKey" )
    private String encryptedPrivateKey;

    @Schema(example = "true", description = "이용 약관 동의 여부")
    private Boolean termsOfServiceAgreement = false;

    public void setTermsOfServiceAgreement(){
        this.termsOfServiceAgreement = true;
    }

    /**
            * JWT 리프레시 토큰 갱신 메서드
     */
    public void updateJwtRefreshToken(String jwtRefreshToken) {
        this.jwtRefreshToken = jwtRefreshToken;
    }
}
