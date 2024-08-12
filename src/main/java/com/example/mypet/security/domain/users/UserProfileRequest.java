package com.example.mypet.security.domain.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserProfileRequest {

    @Schema(description = "사용자 이름", example = "홍길동")
    private String username;

    @Schema(description = "지역(구)", example = "동작구")
    private String location;

    @Schema(description = "사용자 소개", nullable = true)
    private String description;

    @Schema(description = "프로필 이미지 base64", example = "asfashiuhaslkdjf", nullable = true)
    private String base64ProfileImage;

    @Setter
    @Schema(description = "프로필 이미지 url", example = "https://ddsasfdaf.com")
    private String profileImageUrl;

}
