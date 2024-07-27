package com.example.mypet.find.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PetLostBoardRequestDto {

    private String content; // 게시물 내용
    private String location; // 분실 장소
    private String dateTime; // 분실 일시
    private String reporterName; // 신고자 이름
    private String reporterContact; // 신고자 연락처


    @Builder
    public PetLostBoardRequestDto(String content, String location, String dateTime, String reporterName, String reporterContact, String petsId) {
        this.content = content;
        this.location = location;
        this.dateTime = dateTime;
        this.reporterName = reporterName;
        this.reporterContact = reporterContact;

    }
}