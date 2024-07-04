package com.example.mypet.security.domain.users;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.time.Instant;
import java.util.Set;

@NoArgsConstructor
@Data
public class OAuth2AccessTokenDto {
    private OAuth2AccessToken.TokenType tokenType;
    private Set<String> scopes;
    private String tokenValue;
    private Instant issuedAt;
    private Instant expiresAt;

    public OAuth2AccessTokenDto(OAuth2AccessToken oAuth2AccessToken) {
        this.tokenType = oAuth2AccessToken.getTokenType();
        this.scopes = oAuth2AccessToken.getScopes();
        this.tokenValue = oAuth2AccessToken.getTokenValue();
        this.issuedAt = oAuth2AccessToken.getIssuedAt();
        this.expiresAt = oAuth2AccessToken.getExpiresAt();
    }

    public OAuth2AccessTokenDto(OAuth2AccessToken.TokenType tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }
}
