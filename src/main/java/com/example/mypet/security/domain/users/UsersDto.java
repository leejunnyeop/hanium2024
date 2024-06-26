package com.example.mypet.security.domain.users;

import lombok.Getter;

@Getter
public class UserDto {

    private String email;
    private String name;
    private String provider;
    private String refreshToken;
}
