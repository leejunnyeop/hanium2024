package com.example.mypet.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {


    @Schema(example = "Bearer")
    private String grantType;
    private String accessToken;
    private Long accessTokenExpirationTime;
    private String refreshToken;
    private Long refreshTokenExpirationTime;

    @Setter
    @Schema(example = "true", description = "처음 로그인/회원가입 여부 체크")
    private Boolean isFirstLogin = false;
}
