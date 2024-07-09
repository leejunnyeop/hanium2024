package com.example.mypet.security.config;

import com.example.mypet.security.filter.JwtRequestFilter;
import com.example.mypet.security.ex.JwtAuthenticationEntryPoint;
import com.example.mypet.security.handler.CustomAuthenticationSuccessHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtRequestFilter jwtRequestFilter;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   ClientRegistrationRepository clientRegistrationRepository,
                                                   OAuth2AuthorizedClientService authorizedClientService) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .formLogin(AbstractHttpConfigure -> AbstractHttpConfigure.disable())
                .httpBasic(AbstractHttpConfigure -> AbstractHttpConfigure.disable())
                .cors(AbstractHttpConfigurer::disable)

                .logout(AbstractHttpConfigurer -> AbstractHttpConfigurer.disable()) // 기본 logout 비활성화
                .headers(c -> c.frameOptions(
                        FrameOptionsConfig -> FrameOptionsConfig.disable()).disable()) // X-Frame-Options 비활성화
                .sessionManagement(c ->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/token"),
                                new AntPathRequestMatcher("/auth/login/**"),
                                new AntPathRequestMatcher("/auth/success/**"))
                                .permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .clientRegistrationRepository(clientRegistrationRepository)
                        .authorizedClientService(authorizedClientService)
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customOAuth2UserService))
                        .successHandler(customAuthenticationSuccessHandler) // 성공 핸들러 설정
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
        // JwtRequestFilter를 OAuth2LoginAuthenticationFilter 뒤에 추가
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }




    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers( "/error/**", "/favicon.ico",
                "/swagger-ui/**","/api-docs/**","/auth/login/**");
    }
}
