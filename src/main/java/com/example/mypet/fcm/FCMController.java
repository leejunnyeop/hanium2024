package com.example.mypet.fcm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/notifications")
@Tag(name = "Notification API", description = "푸시 알림을 전송하는 API")
@RequiredArgsConstructor
public class FCMController {

    private final FCMService fcmService;



    @Operation(summary = "강아지를 찾아요 게시판 작성시 푸쉬알람", description = "FCM 토큰 리스트를 받아 모든 사용자에게 푸시 알림을 전송하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "알림 전송 성공"),
            @ApiResponse(responseCode = "500", description = "알림 전송 실패", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/send")
    public ResponseEntity<Void> sendNotifications(
            @RequestBody @Schema(description = "FCM 토큰 리스트", example = "[\"token1\", \"token2\"]") List<String> tokens,
            @RequestParam(name = "boardId") @Schema(description = "게시글 ID", example = "12345") String boardId) {
        try {
            fcmService.sendMessagesToTokens(tokens, boardId);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}