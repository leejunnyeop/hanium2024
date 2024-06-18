package com.example.mypet.security.jwt.jwtToken;

public interface JwtTokenStrategy {

    String generateAccessToken(String username);
    String generateRefreshToken(String username);
    boolean validateToken(String token, String username);
    String extractUsername(String token);

}
