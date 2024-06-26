package com.example.mypet.security.domain.users;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsersDto {

    private String email;
    private String name;
    private String provider;

    public UsersDto(String email, String name, String provider) {
        this.email = email;
        this.name = name;
        this.provider = provider;
    }


}
