package com.example.mypet.fcm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/notifications")
@Tag(name = "notification", description = "푸시 알림을 전송하는 API")
@RequiredArgsConstructor
public class FCMController {

    private final FCMService fcmService;
    @Operation(summary = "강아지를 찾아요 게시판 작성시 푸쉬알람", description = "FCM 토큰 리스트를 받아 모든 사용자에게 푸시 알림을 전송하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 전송 성공"),
            @ApiResponse(responseCode = "500", description = "알림 전송 실패", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/send/{boardId}")
    @Parameters({
            @Parameter(name = "boardId", description = "게시물 ID", example = "board123")
    })
    public ResponseEntity<Void> sendNotifications(
            @PathVariable String boardId) {
        try {
            fcmService.sendMessagesToAll(boardId);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/fcm-token")
    public ResponseEntity<String> updateFcmToken(@AuthenticationPrincipal User user, @RequestBody String fcmToken) {
        fcmService.updateFcmToken(user.getUsername(), fcmToken);
        return ResponseEntity.ok("FCM Token updated successfully");
    }
}