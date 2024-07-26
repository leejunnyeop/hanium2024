package com.example.mypet.security.domain.users;

import com.example.mypet.pet.domain.entity.Pets;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

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

    @DBRef
    @Schema(example = "강아지 연관관계", description = "강아지 관련 정보")
    private List<Pets> pets = new ArrayList<>();

    @Schema(description = "지역(구)", example = "동작구")
    private String location;

    @Schema(description = "소개(자랑)", example = "우리 퍼피를 사랑하는 주인")
    private String description;

    private String profileUrl;

    public void userProfileUpdate(UserProfileRequest userProfileRequest){
        if (userProfileRequest.getLocation() != null){
            this.location = userProfileRequest.getLocation();
        }
        if (userProfileRequest.getDescription() != null){
            this.description = userProfileRequest.getDescription();
        }
        if (userProfileRequest.getProfileUrl() != null){
            this.profileUrl = userProfileRequest.getProfileUrl();;
        }
    }


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
