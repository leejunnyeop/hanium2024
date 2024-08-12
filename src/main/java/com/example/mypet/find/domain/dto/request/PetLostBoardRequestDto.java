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
public class PetLostBoardRequestDto {


    @Schema(description = "제목", example = "마루를 찾아주세요", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "제목은 필수 값입니다.")
    private String title;

    @Schema(description = "강아지 사진, base64로 된 이미지", example = "/asfsacasdewahstrjuru/", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String base64Image;



    @Schema(description = "강아지 이름 (직접 입력할 때 사용)", example = "코코", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String petName;

    @Schema(description = "연락 수단", example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "신고자 연락처는 필수 값입니다.")
    private String reporterContact;

    @Schema(description = "발견 위치", example = "삼성동 코엑스 근처", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "구체적인 실종 장소는 필수 값입니다.")
    private String specificLocation;

    @Schema(description = "발견 일시", example = "2023-08-03T10:15:30", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "실종 일시는 필수 값입니다.")
    private String missingDateTime;

    @Schema(description = "발견 경위", example = "산책 중 잠깐 놓쳤습니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "실종 경위는 필수 값입니다.")
    private String missingSituation;




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

    @Schema(description = "이미지 URL (직접 입력할 때 사용)", example = "http://example.com/image.jpg", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String petImageUrl;

    @Builder
    public PetLostBoardRequestDto(String content, String city, String district, String specificLocation, String missingDateTime, String missingSituation, String reporterName, String reporterContact, String petName, Gender petGender, LocalDate petBirthDate, String petBreed, String petDescription, Boolean petNeutered, String petImageUrl) {
        this.content = content;

        this.specificLocation = specificLocation;
        this.missingDateTime = missingDateTime;
        this.missingSituation = missingSituation;
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