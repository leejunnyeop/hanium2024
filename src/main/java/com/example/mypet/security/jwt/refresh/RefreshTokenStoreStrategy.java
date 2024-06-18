package com.example.mypet.security.jwt.refresh;

public interface RefreshTokenStoreStrategy {

    void saveRefreshToken(String username, String refreshToken);
    String getRefreshToken(String username);
    void deleteRefreshToken(String username);
}
