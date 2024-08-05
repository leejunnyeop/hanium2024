package com.example.mypet.find.domain.dto.request;

import com.example.mypet.pet.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PetLostBoardRequestDto {


    @Schema(description = "제목", example = "퍼리를 찾아주세요", required = true)
    @NotNull(message = "제목은 필수 값입니다.")
    private String title;

    @Schema(description = "지역(시)", example = "서울특별시", required = true)
    @NotNull(message = "지역(시)은 필수 값입니다.")
    private String city;

    @Schema(description = "지역(구)", example = "강남구", required = true)
    @NotNull(message = "지역(구)은 필수 값입니다.")
    private String district;

    @Schema(description = "구체적인 실종 장소", example = "삼성동 코엑스 근처", required = true)
    @NotNull(message = "구체적인 실종 장소는 필수 값입니다.")
    private String specificLocation;

    @Schema(description = "실종 일시", example = "2023-08-03T10:15:30", required = true)
    @NotNull(message = "실종 일시는 필수 값입니다.")
    private String missingDateTime;

    @Schema(description = "실종 경위", example = "산책 중 잠깐 놓쳤습니다.", required = true)
    @NotNull(message = "실종 경위는 필수 값입니다.")
    private String missingSituation;

    @Schema(description = "신고자 이름", example = "홍길동", required = true)
    @NotNull(message = "신고자 이름은 필수 값입니다.")
    private String reporterName;

    @Schema(description = "신고자 연락처", example = "010-1234-5678", required = true)
    @NotNull(message = "신고자 연락처는 필수 값입니다.")
    private String reporterContact;

    @Schema(description = "강아지 이름 (직접 입력할 때 사용)", example = "코코", required = false)
    private String petName;

    @Schema(description = "강아지 성별 (직접 입력할 때 사용)", example = "MALE", required = false)
    private Gender petGender;

    @Schema(description = "강아지 생년월일 (직접 입력할 때 사용)", example = "2020-01-01", required = false)
    private LocalDate petBirthDate;

    @Schema(description = "강아지 품종 (직접 입력할 때 사용)", example = "시베리안 허스키", required = false)
    private String petBreed;

    @Schema(description = "강아지 외형 (직접 입력할 때 사용)", example = "활발하고 친근한 강아지", required = false)
    private String petDescription;

    @Schema(description = "기타", example = "강아지를 잃어버렸어요. 사례금 많이 드리겠습니다", required = true)
    private String content;

    @Schema(description = "중성화 여부 (직접 입력할 때 사용)", example = "true", required = false)
    private Boolean petNeutered;

    @Schema(description = "이미지 URL (직접 입력할 때 사용)", example = "http://example.com/image.jpg", required = false)
    private String petImageUrl;

    @Builder
    public PetLostBoardRequestDto(String content, String city, String district, String specificLocation, String missingDateTime, String missingSituation, String reporterName, String reporterContact, String petName, Gender petGender, LocalDate petBirthDate, String petBreed, String petDescription, Boolean petNeutered, String petImageUrl) {
        this.content = content;
        this.city = city;
        this.district = district;
        this.specificLocation = specificLocation;
        this.missingDateTime = missingDateTime;
        this.missingSituation = missingSituation;
        this.reporterName = reporterName;
        this.reporterContact = reporterContact;
        this.petName = petName;
        this.petGender = petGender;
        this.petBirthDate = petBirthDate;
        this.petBreed = petBreed;
        this.petDescription = petDescription;
        this.petNeutered = petNeutered;
        this.petImageUrl = petImageUrl;
    }
}