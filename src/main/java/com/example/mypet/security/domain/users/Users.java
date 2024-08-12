package com.example.mypet.security.domain.users;

import com.example.mypet.pet.domain.entity.Pets;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @Schema(example = "사용자 소개", description = "저는 마루를 키우는 착한 사람이에요")
    private String description;

    @DBRef
    @Schema(example = "강아지 연관관계", description = "강아지 관련 정보")
    private List<Pets> pets = new ArrayList<>();

    @Schema(description = "지역(구)", example = "동작구")
    private String location;

    @Schema(description = "사용자 프로필 이미지 url", example = "user profile image")
    private String profileImageUrl;

    public void userProfileUpdate(UserProfileRequest userProfileRequest){
        if (userProfileRequest.getLocation() != null){
            this.location = userProfileRequest.getLocation();
        }
        if (userProfileRequest.getDescription() != null && !userProfileRequest.getDescription().isEmpty()){
            this.description = userProfileRequest.getDescription();
        }
        if (userProfileRequest.getUsername() != null && !userProfileRequest.getUsername().isEmpty()) {
            this.name = userProfileRequest.getUsername();
        }

        if (userProfileRequest.getProfileImageUrl() != null && !userProfileRequest.getProfileImageUrl().isEmpty()){
            this.profileImageUrl = userProfileRequest.getProfileImageUrl();
        }
        else {
            Random random = new Random();
            int randomNumber = random.nextInt(8) + 1;
            this.profileImageUrl = "https://happymaru-bucket.s3.ap-northeast-2.amazonaws.com/random-person/person-" + randomNumber + ".png";
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
