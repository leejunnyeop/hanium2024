package com.example.mypet.security.jwt.jwtToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class DefaultJwtTokenStrategy implements JwtTokenStrategy {

    private static final String SECRETKEY = "petkey" ;

    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 15 * 60; // 15분
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60; // 30일

    // 비밀 키를 SecretKey 객체로 변환하는 메소드
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRETKEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_VALIDITY_SECONDS);
    }

    private String generateToken(String subject, long validity) {
        Map<String, Object> claims = new HashMap<>();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validity * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
