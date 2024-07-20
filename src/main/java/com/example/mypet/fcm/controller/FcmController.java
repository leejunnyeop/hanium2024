package com.example.mypet.fcm.controller;

import com.example.mypet.fcm.dto.FcmSendDto;
import com.example.mypet.fcm.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fcm")
public class FcmController {

    private final FcmService fcmService;


    @PostMapping("/send")
    public ResponseEntity<Integer> sendNotification(@RequestBody FcmSendDto fcmRequestDto) throws IOException {
        int successCount = fcmService.sendNotificationToUsersInLocation(fcmRequestDto);
        return ResponseEntity.ok(successCount);
    }
}
