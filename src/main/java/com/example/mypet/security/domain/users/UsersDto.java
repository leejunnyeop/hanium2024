package com.example.mypet.security.domain.users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersDto {

    private String email;
    private String name;
    private String provider;
    private String jwtRefreshToken;
    private String accessToken;
    private String refreshToken;
    private String clientRegistrationId;

}
