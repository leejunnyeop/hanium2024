package com.example.mypet.find.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OwnerFoundBoardRequestDto {

    private String content; // 게시물 내용
    private String location; // 발견 장소
    private String dateTime; // 발견 일시
    private String reporterName; // 신고자 이름
    private String reporterContact; // 신고자 연락처

    @Builder
    public OwnerFoundBoardRequestDto(String content, String location, String dateTime, String reporterName, String reporterContact) {
        this.content = content;
        this.location = location;
        this.dateTime = dateTime;
        this.reporterName = reporterName;
        this.reporterContact = reporterContact;
    }
}