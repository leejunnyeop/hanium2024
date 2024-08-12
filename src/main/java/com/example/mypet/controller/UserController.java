package com.example.mypet.controller;


import com.example.mypet.security.domain.users.UserProfileRequest;
import com.example.mypet.security.domain.users.Users;
import com.example.mypet.service.KmsService;
import com.example.mypet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "사용자 API")
public class UserController {

    private final UserService userService;
    private final KmsService kmsService;
    @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<Users> getUserInfo(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getUserInfo(user.getUsername()), HttpStatus.OK) ;
    }

    @Operation(summary = "동의", description = "서비스 사용 동의하기.")
    @PatchMapping("/agree")
    public ResponseEntity<Users> changeTermsOfServiceAgreement(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.changeTermsOfServiceAgreement(user.getUsername()), HttpStatus.OK);
    }

    @Operation(summary = "decode", description = "암호화 되었던 private key 반환.")
    @GetMapping("/decode")
    public ResponseEntity<String> getDecodedPrivateKey(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.decodeToken(user.getUsername()), HttpStatus.OK);
    }


    @Operation(summary = "update profile", description = "사용자 정보 업데이트")
    @PutMapping("/profile")
    public ResponseEntity<Void> updateUserProfile(@AuthenticationPrincipal User user, @RequestBody UserProfileRequest userProfileRequest) throws IOException {
        userService.updateUserProfile(user.getUsername(), userProfileRequest);
        return ResponseEntity.ok().build();
    }


}
