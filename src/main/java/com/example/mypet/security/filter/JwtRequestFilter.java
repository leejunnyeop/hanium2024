package com.example.mypet.security.filter;

import com.example.mypet.security.jwt.jwtToken.JwtTokenStrategy;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        log.info("OncePerRequestFilter 시작");

        // 소셜 로그인 경로를 무시
        String path = request.getServletPath();
        if (path.startsWith("/login/oauth2") || path.startsWith("/oauth2")) {
            chain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenStrategy.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                handleException(response, HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
                return;
            } catch (ExpiredJwtException e) {
                handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 만료되었습니다.");
                return;
            } catch (MalformedJwtException e) {
                handleException(response, HttpServletResponse.SC_BAD_REQUEST, "JWT 형식이 잘못되었습니다.");
                return;
            }
        } else {
            log.info("JWT 토큰이 Bearer 문자열로 시작하지 않습니다.");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenStrategy.validateToken(jwtToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.sendError(statusCode, message);
    }
}
