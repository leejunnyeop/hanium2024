package com.example.mypet.find.domain.dto.response;

import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.pet.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LostAndFoundPetsBoardDetailResponseDto {

    private String id; // 게시물 ID
    private String content; // 게시물 내용
    private String imageUrl; // 강아지 이미지 URL
    private String location; // 발견/분실 장소
    private String dateTime; // 발견/분실 일시
    private String reporterName; // 신고자 이름
    private String reporterContact; // 신고자 연락처
    private PostType postType; // 게시글 유형 (OWNER, PET)
    private String breed; // 강아지 견종
    private Gender gender; // 강아지 성별
    private String description; // 강아지 특징 및 특이사항
    private boolean hasNoseImg; // 비문 등록 여부
    private int age; // 나이


}