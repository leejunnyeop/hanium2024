package com.example.mypet.healthComment;


import com.example.mypet.healthComment.dto.HealthCommentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
@Tag(name = "comment", description = "사용자 펫 코멘트 API")
public class HealthCommentController {
    private final HealthCommentService healthCommentService;

    @Operation(summary = "펫 코멘트 등록", description = "펫의 코멘트를 등록 및 업데이트합니다.")
    @PostMapping
    public ResponseEntity<HealthCommentDto> saveHealthComment(
            @AuthenticationPrincipal User user,
            @RequestBody HealthCommentDto healthCommentDto) {
        return ResponseEntity.ok().body(healthCommentService.saveHealthComment(user.getUsername(), healthCommentDto));
    }

    @Operation(summary = "펫 코멘트 조회", description = "펫의 코멘트를 조회합니다.")
    @GetMapping("/{date}")
    @Parameters({@Parameter(name = "date", description = "코멘트를 조회할 날짜 형식", example = "2023-07-20")})
    public ResponseEntity<HealthCommentDto> getHealthComment(
            @AuthenticationPrincipal User user,
            @PathVariable LocalDate date) {
        return ResponseEntity.ok().body(healthCommentService.getHealthComment(user.getUsername(), date));
    }
}
