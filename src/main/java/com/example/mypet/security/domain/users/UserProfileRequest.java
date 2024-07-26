package com.example.mypet.security.domain.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UserProfileRequest {

    @Schema(description = "지역(구)", example = "동작구")
    private String location;

    @Schema(description = "소개(자랑)", example = "우리 퍼피를 사랑하는 주인")
    private String description;

    private String profileUrl;

}
