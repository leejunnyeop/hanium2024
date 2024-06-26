package com.example.mypet.security.domain.users;

public class UserMapper {

    public static UsersDto toDto(Users user) {
        return UsersDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .provider(user.getProvider())
                .build();
    }

    public static Users toEntity(UsersDto userDto) {
        return Users.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .provider(userDto.getProvider())
                .build();
    }
}
