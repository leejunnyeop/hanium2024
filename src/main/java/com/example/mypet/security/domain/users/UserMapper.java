package com.example.mypet.security.domain.users;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

public class UserMapper {

    public static UsersDto toDto(Users user) {
        return UsersDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .provider(user.getProvider())
                .jwtRefreshToken(user.getJwtRefreshToken())
                .accessToken(user.getAccessToken() != null ? user.getAccessToken().getTokenValue() : null)
                .refreshToken(user.getRefreshToken() != null ? user.getRefreshToken().getTokenValue() : null)
                .clientRegistrationId(user.getClientRegistrationId())
                .build();
    }

    public static Users toEntity(UsersDto userDto) {
        return Users.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .provider(userDto.getProvider())
                .jwtRefreshToken(userDto.getJwtRefreshToken())
                .accessToken(userDto.getAccessToken() != null ? new OAuth2AccessTokenDto(OAuth2AccessToken.TokenType.BEARER, userDto.getAccessToken()) : null)
                .refreshToken(userDto.getRefreshToken() != null ? new OAuth2RefreshToken(userDto.getRefreshToken(), null) : null)
                .clientRegistrationId(userDto.getClientRegistrationId())
                .build();
    }
}
