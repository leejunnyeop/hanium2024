package com.example.mypet.security.service.tokenService;

import com.example.mypet.security.domain.oauth.OAuth2AuthorizedClientEntity;
import com.example.mypet.security.domain.oauth.OAuth2UserInfo;
import com.example.mypet.security.domain.refresh.AuthResponse;
import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import com.example.mypet.security.domain.users.UserMapper;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.domain.users.UsersDto;
import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import com.example.mypet.security.repository.OAuth2AuthorizedClientRepository;
import com.example.mypet.security.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtTokenStrategy jwtTokenStrategy;
    private final UsersRepository usersRepository;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;


    public AuthResponse handleOAuth2Callback(String provider, String code, String codeVerifier) {
        Map<String, Object> tokenResponse = (Map<String, Object>) tokenService.getAccessTokenResponse(provider, code, codeVerifier);
        String accessToken = (String) tokenResponse.get("access_token");
        String refreshToken = (String) tokenResponse.get("refresh_token");
        OAuth2UserInfo userInfo = userService.getOAuth2UserInfo(provider, accessToken);
        UsersDto userDto = userService.processUser(userInfo, provider);
        Users user = UserMapper.toEntity(userDto);



        // Jwt 테스트 할라면 : 위에 거 주석 하고 테스트 jwt
        String jwtAccessToken = jwtTokenStrategy.generateAccessToken(user.getEmail());
        String jwtRefreshToken = jwtTokenStrategy.generateRefreshToken(user.getEmail());

        Users updatedUser = user.toBuilder().refreshToken(jwtRefreshToken).build();
        usersRepository.save(updatedUser);

        OAuth2AuthorizedClientEntity clientEntity = OAuth2AuthorizedClientEntity.builder()
                .clientRegistrationId(provider)
                .principalName(user.getEmail())
                .accessToken(new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessToken, null, null))  // Set appropriate dates
                .refreshToken(new OAuth2RefreshToken(refreshToken, null))  // Set appropriate dates
                .build();
        authorizedClientRepository.save(clientEntity);


        return new AuthResponse(jwtAccessToken, jwtRefreshToken);
    }

    public OAuth2AccessTokenResponse refreshAccessToken(String refreshToken) {
        String email = jwtTokenStrategy.extractUsername(refreshToken);
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 리프레시 토큰입니다."));

        if (!jwtTokenStrategy.validateToken(refreshToken, email)) {
            throw new IllegalArgumentException("잘못된 리프레시 토큰입니다.");
        }

        String newAccessToken = jwtTokenStrategy.generateAccessToken(email);
        String newRefreshToken = jwtTokenStrategy.generateRefreshToken(email);

        user = user.toBuilder().refreshToken(newRefreshToken).build();
        usersRepository.save(user);

        return new OAuth2AccessTokenResponse(newAccessToken, newRefreshToken);
    }





}
