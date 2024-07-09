package com.example.mypet.controller;

import com.example.mypet.dto.TokenInfo;
import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import com.example.mypet.security.domain.refresh.RefreshTokenRequest;
import com.example.mypet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    // Todo
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken(@RequestBody RefreshTokenRequest request) {
//        OAuth2AccessTokenResponse response = authService.refreshAccessToken(request.getRefreshToken());
        return ResponseEntity.ok("refresh");
    }

    // useless
    @GetMapping("/success")
    public String home(){
        return "home";
    }

    @Operation(summary = "소셜 로그인")
    @PostMapping("/login/{provider}")
    public ResponseEntity<TokenInfo> socialLogin(@RequestHeader(name = "Authorization") String socialToken, @PathVariable String provider){
        System.out.println("social Token: " + socialToken);
        TokenInfo tokenInfo = authService.socialLogin(socialToken, provider);
        return new ResponseEntity<>(tokenInfo, HttpStatus.OK) ;
    }

}
