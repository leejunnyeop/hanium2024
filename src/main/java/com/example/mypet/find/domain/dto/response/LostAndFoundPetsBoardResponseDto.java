package com.example.mypet.find.domain.dto.response;

import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.pet.domain.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LostAndFoundPetsBoardResponseDto {


    private String id; // 게시물 ID
    private String title;
    private String imageUrl; // 강아지 이미지 URL
    private String location; // 발견/분실 장소
    private String dateTime; // 발견/분실 일시
    private String breed; // 강아지 견종
    private String petDescription; // 외형
    private Gender gender; // 강아지 성별
}
