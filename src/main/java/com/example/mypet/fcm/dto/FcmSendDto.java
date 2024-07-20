package com.example.mypet.fcm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class FcmSendDto {

    private String token;

    private String title;

    private String body;

    private String imageUrl;

    private String location;  // 위치 정보 추가

    @Builder(toBuilder = true)

    public FcmSendDto(String token, String title, String body, String imageUrl, String location) {
        this.token = token;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.location = location;
    }
}
