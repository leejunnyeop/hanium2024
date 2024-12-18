package com.example.mypet.service;


import com.example.mypet.dto.TokenInfo;
import com.example.mypet.enums.Role;

import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.jwt.JwtExpireTime;
import com.example.mypet.security.libs.SocialLoginUtils;
import com.example.mypet.security.repository.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Service
public class AuthService {

    private final SocialLoginUtils socialLoginUtils;
    private final UsersRepository usersRepository;
    private final UserService userService;


    @Value("${jwt.token.secret-key}")
    private String secretKey;

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";

    public TokenInfo socialLogin(String socialToken, String provider) {

        var jsonValue = getMemberInfoByAccessToken(socialToken, provider);

        if (jsonValue == null) {
            // Todo: Custom Exception 처리
            throw new IllegalArgumentException("잘못된 provider" + provider);
        }
        // Todo: Exception 처리 좀 더 깔끔하게
        // 회원정보에 없으면 가입처리
        var email = jsonValue.getString("email");
        Users user = usersRepository.findByEmail(email).orElse(null);
        
        // 처음 회원가입 여부 -> 온보딩에 필요함
        var isFirst = false;
        if (user == null) {
            isFirst = true;
            user = userService.registerMember(jsonValue, provider);
        }
       

        Date now = new Date();
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name()));

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        // generate token

        String accessToken = Jwts.builder()
                .setSubject(user.getId())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("type", TYPE_ACCESS)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtExpireTime.ACCESS_TOKEN_EXPIRE_TIME))  //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        //Generate RefreshToken
        String refreshToken = Jwts.builder()
                .claim("type", TYPE_REFRESH)
                .setIssuedAt(now)   //토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtExpireTime.REFRESH_TOKEN_EXPIRE_TIME)) //토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        var tokenInfo =  TokenInfo.builder()
                .isFirstLogin(true)
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpirationTime(JwtExpireTime.ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(JwtExpireTime.REFRESH_TOKEN_EXPIRE_TIME)
                .build();
        if (!isFirst)
            tokenInfo.setIsFirstLogin(false);
//        user.updateTokens(accessToken);

        user.updateJwtRefreshToken(refreshToken);
        usersRepository.save(user);

        return tokenInfo;
    }

    private JSONObject getMemberInfoByAccessToken(String accessToken, String provider){


        JSONObject json = null;
        try {
            switch (provider) {
                case "naver":
//                    authProviderId = socialLoginUtils.getNaverInfo(accessToken);
                    break;
                case "google":

                    json = socialLoginUtils.getGoogleInfo(accessToken);

                    break;
                default:

                    throw new IllegalArgumentException("지원하지 않는 provieder" + provider);
            }
        }catch (Exception e){

        }

        return json;
    }


}
