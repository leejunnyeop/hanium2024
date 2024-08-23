package com.example.mypet.alarm;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
@Tag(name = "alarm", description = "alarm 관련 API")
public class AlarmController {

    private final AlarmService alarmService;

    @Operation(summary = "사용자의 모든 알람 반환", description = "사용자의 모든 알람을 반환합니다.")
    @GetMapping
    public ResponseEntity<List<AlarmResponseDto>> getAlarm(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(alarmService.getAlarm(user.getUsername()));
    }

    @Operation(summary = "사용자의 알람 읽은 표시", description = "사용자가 알람을 읽었으면 true로 만들어줍니다.")
    @PatchMapping("/{alarmId}")
    public ResponseEntity<Void> readAlarm(@AuthenticationPrincipal User user, @PathVariable String alarmId) {
        alarmService.markAsRead(alarmId);
        return ResponseEntity.noContent().build();
    }
}
