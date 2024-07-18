package com.example.mypet.controller;

import com.example.mypet.dto.TokenInfo;
import com.example.mypet.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Log4j2
@RestController
@RequestMapping("/auth")
@Tag(name = "auth", description = "회원가입/로그인 API")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    // Todo
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshAccessToken() {
        return ResponseEntity.ok("refresh");
    }

    // useless
    @GetMapping("/success")
    public String home(){
        return "home";
    }

    @Operation(summary = "소셜 로그인", responses = { @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenInfo.class)))})
    @PostMapping("/login/{provider}")
    public ResponseEntity<TokenInfo> socialLogin(@RequestHeader(name = "Authorization") String socialToken, @PathVariable(name = "provider") String provider){

        log.info("social Token: " + socialToken);
        TokenInfo tokenInfo = authService.socialLogin(socialToken, provider);
        return new ResponseEntity<>(tokenInfo, HttpStatus.OK) ;
    }

}
