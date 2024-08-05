package com.example.mypet.find.domain.entity;

import com.example.mypet.pet.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "lost_and_found_pets_board")
public class LostAndFoundPetsBoard {

    @Id
    private String id;
    private String title;
    private String content; // 게시물 내용
    private String city; // 지역(시)
    private String district; // 지역(구)
    private String specificLocation; // 구체적인 실종 장소
    private String missingDateTime; // 실종 일시
    private String missingSituation; // 실종 경위
    private String reporterName; // 신고자 이름
    private String reporterContact; // 신고자 연락처
    private PostType postType; // 게시글 유형 (OWNER, PET)

    // 강아지 정보

    private String petName; // 강아지 이름
    private Gender petGender; // 강아지 성별
    private LocalDate petBirthDate; // 강아지 생년월일
    private String petBreed; // 강아지 품종
    private String petDescription; // 강아지 설명
    private Boolean petNeutered; // 중성화 여부
    private String petImageUrl; // 이미지 URL
    private int age; // 나이

    public void calculateAge() {
        if (this.petBirthDate != null) {
            this.age = Period.between(this.petBirthDate, LocalDate.now()).getYears();
        } else {
            this.age = 0;
        }

    }
}