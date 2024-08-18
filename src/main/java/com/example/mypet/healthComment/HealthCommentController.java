package com.example.mypet.healthComment;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
@Tag(name = "comment", description = "사용자 펫 커멘트 API")
public class HealthCommentController {
    private final HealthCommentService healthCommentService;

    @GetMapping("/{date}")
    public ResponseEntity<?> getHealthComment(
            @AuthenticationPrincipal User user,
            @Parameter(description = "커멘트를 조회할 날짜 형식", example = "2023-07-20") @PathVariable LocalDate date) {
        return ResponseEntity.ok().body(healthCommentService.getHealthComment(user.getUsername(), date));
    }
}
