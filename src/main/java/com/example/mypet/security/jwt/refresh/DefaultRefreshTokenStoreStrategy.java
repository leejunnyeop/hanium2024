package com.example.mypet.security.jwt;

import com.example.mypet.security.domain.RefreshToken;
import com.example.mypet.security.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DefaultRefreshTokenStoreStrategy implements RefreshTokenStoreStrategy {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void saveRefreshToken(String email, String refreshToken) {
        RefreshToken token = refreshTokenRepository.findByEmail(email);
        if (token == null) {
            token = new RefreshToken(email, refreshToken);
        } else {
            token.withNewToken(refreshToken);
        }
        refreshTokenRepository.save(token);
    }

    @Override
    public String getRefreshToken(String email) {
        RefreshToken token = refreshTokenRepository.findByEmail(email);
        return token != null ? token.getToken() : null;
    }

    @Override
    public void deleteRefreshToken(String email) {
        refreshTokenRepository.deleteByEmail(email);
    }
}
