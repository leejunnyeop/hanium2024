package com.example.mypet.security.filter;

import com.example.mypet.security.jwt.JwtTokenStrategy;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

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

public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenStrategy jwtTokenStrategy;
    private final UserDetailsService jwtUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        System.out.println(requestTokenHeader);
        // JWT 토큰은 "Bearer " 문자열로 시작하는지 확인
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenStrategy.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                handleException(response, HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");

            } catch (ExpiredJwtException e) {
                handleException(response, HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 만료되었습니다.");

            }
            catch (MalformedJwtException e) {
                handleException(response, HttpServletResponse.SC_BAD_REQUEST, "JWT 형식이 잘못되었습니다.");
            }
        }
        else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        // 토큰을 받은 후 유효성 검사
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenStrategy.validateToken(jwtToken, userDetails)) {

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
