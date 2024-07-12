package com.example.mypet.controller;

import com.example.mypet.security.domain.users.Users;
import com.example.mypet.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user", description = "사용자 API")
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<Users> getUserInfo(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.getUserInfo(user.getUsername()), HttpStatus.OK) ;
    }

    @PatchMapping
    public ResponseEntity<Users> changeTermsOfServiceAgreement(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userService.changeTermsOfServiceAgreement(user.getUsername()), HttpStatus.OK);
    }
}
