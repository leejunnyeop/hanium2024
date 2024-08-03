package com.example.mypet.find.domain.dto.request;

import com.example.mypet.pet.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class OwnerFoundBoardRequestDto {

    @Schema(description = "제목", example = "주인을 찾아요", required = true)
    @NotNull(message = "제목은 필수 값입니다.")
    private String title;



    @Schema(description = "지역(시)", example = "서울특별시", required = true)
    @NotNull(message = "지역(시)은 필수 값입니다.")
    private String city;

    @Schema(description = "지역(구)", example = "강남구", required = true)
    @NotNull(message = "지역(구)은 필수 값입니다.")
    private String district;

    @Schema(description = "구체적인 발견 장소", example = "삼성동 코엑스 근처", required = true)
    @NotNull(message = "구체적인 발견 장소는 필수 값입니다.")
    private String specificLocation;

    @Schema(description = "발견 일시", example = "2023-08-03T10:15:30", required = true)
    @NotNull(message = "발견 일시는 필수 값입니다.")
    private String foundDateTime;

    @Schema(description = "발견 상황", example = "산책 중 발견했습니다.", required = true)
    @NotNull(message = "발견 상황은 필수 값입니다.")
    private String situation;

    @Schema(description = "신고자 이름", example = "홍길동", required = true)
    @NotNull(message = "신고자 이름은 필수 값입니다.")
    private String reporterName;

    @Schema(description = "신고자 연락처", example = "010-1234-5678", required = true)
    @NotNull(message = "신고자 연락처는 필수 값입니다.")
    private String reporterContact;

    @Schema(description = "강아지 이름", example = "코코", required = true)
    @NotNull(message = "강아지 이름은 필수 값입니다.")
    private String petName;

    @Schema(description = "강아지 성별", example = "MALE", required = true)
    @NotNull(message = "강아지 성별은 필수 값입니다.")
    private Gender petGender;


    @Schema(description = "강아지 품종", example = "시베리안 허스키", required = true)
    @NotNull(message = "강아지 품종은 필수 값입니다.")
    private String petBreed;

    @Schema(description = "강아지 외형 설명", example = "활발하고 친근한 강아지", required = true)
    @NotNull(message = "강아지 설명은 필수 값입니다.")
    private String petDescription;

    @Schema(description = "기타", example = "사례금 천만원 입니다", required = true)
    private String content;

    @Schema(description = "중성화 여부", example = "true", required = true)
    @NotNull(message = "중성화 여부는 필수 값입니다.")
    private Boolean petNeutered;

    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg", required = true)
    @NotNull(message = "이미지 URL은 필수 값입니다.")
    private String petImageUrl;

    @Builder
    public OwnerFoundBoardRequestDto(String content, String title, String city, String district, String specificLocation, String foundDateTime, String situation, String reporterName, String reporterContact, String petName, Gender petGender, String petBreed, String petDescription, Boolean petNeutered, String petImageUrl) {
        this.content = content;
        this.title = title;
        this.city = city;
        this.district = district;
        this.specificLocation = specificLocation;
        this.foundDateTime = foundDateTime;
        this.situation = situation;
        this.reporterName = reporterName;
        this.reporterContact = reporterContact;
        this.petName = petName;
        this.petGender = petGender;
        this.petBreed = petBreed;
        this.petDescription = petDescription;
        this.petNeutered = petNeutered;
        this.petImageUrl = petImageUrl;
    }
}