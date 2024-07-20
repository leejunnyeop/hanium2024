package com.example.mypet.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmMessageDto {
    private boolean vailDateOnly;
    private FcmMessageDto.Message message;

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {

        private FcmMessageDto.Notification notification;
        private String token;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Notification {

        private String title;

        private String body;

        private String image;
    }
}
