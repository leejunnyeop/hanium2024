package com.example.mypet.security.jwt;

import com.example.mypet.dto.TokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component

public class JwtTokenStrategy {

    private final Key key;


    private UserDetailsService jwtUserDetailsService;

    // 더 긴 비밀 키 (256비트 이상)
    @Value("${jwt.token.secret-key}")
    private String secret;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 15 * 60; // 15분
    private static final long REFRESH_TOKEN_VALIDITY_SECONDS = 30 * 24 * 60 * 60; // 30일


    public JwtTokenStrategy(@Value("${jwt.token.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰에서 사용자 이름 가져오기
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // 비밀키를 이용하여 JWT 토큰에서 정보 추출하기
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // JWT 토큰이 만료되었는지 확인
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // 사용자 정보를 바탕으로 JWT 액세스 토큰을 생성합니다.
    public String generateAccessToken(UserDetails userDetails) {
        Date now = new Date();
        String authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("type", TYPE_ACCESS)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtExpireTime.ACCESS_TOKEN_EXPIRE_TIME))  //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 사용자 정보를 바탕으로 JWT 리프레시 토큰을 생성합니다.
    public String generateRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtExpireTime.REFRESH_TOKEN_EXPIRE_TIME)) //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public TokenInfo generateToken(UserDetails userDetails) {

        // generate access token
        String accessToken = generateAccessToken(userDetails);
        // generate refresh token
        String refreshToken = generateRefreshToken();

        return TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpirationTime(JwtExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(JwtExpireTime.REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    // JWT 토큰이 유효한지 확인합니다.
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 리프레시 토큰을 검증하고 새로운 액세스 토큰을 발급합니다.
    public String refreshAccessToken(String refreshToken) {
        if (isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh token is expired");
        }
        Claims claims = getAllClaimsFromToken(refreshToken);
        String username = claims.getSubject();

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username); // 여기에 UserDetailsService를 사용하여 userDetails를 가져오는 로직을 추가
        return generateAccessToken(userDetails);
    }

}
