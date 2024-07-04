package com.example.mypet.security.handler;

import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.jwt.jwtToken.DefaultJwtTokenStrategy;
import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import com.example.mypet.security.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final UsersRepository usersRepository;
    private static final String URI = "/auth/success";



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info(" 소셜 로그인 인증 후 jwt 발급 과정 ");
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String email = (String) oAuth2User.getAttributes().get("email");
        String provider = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId(); // 동적 클라이언트 등록 ID

        // JWT 토큰 생성
        String jwtAccessToken = jwtTokenStrategy.generateAccessToken(email);
        String jwtRefreshToken = jwtTokenStrategy.generateRefreshToken(email);

        // 사용자 정보와 토큰 저장
        Users user = usersRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("없는 유저 입니다. "));
        user.updateJwtRefreshToken(jwtRefreshToken); // 사용자 엔티티에 JWT 토큰 업데이트 메서드 사용
        usersRepository.save(user);


        // 토큰 전달을 위한 redirect

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("Authorization", "Bearer " + jwtAccessToken)
                .build().toUriString();

        response.sendRedirect(redirectUrl);

    }
}
