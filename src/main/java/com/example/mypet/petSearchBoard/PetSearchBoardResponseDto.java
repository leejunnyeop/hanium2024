package com.example.mypet.petSearchBoard;

import com.example.mypet.pet.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

@Builder
@Getter
public class PetSearchBoardResponseDto {
    @Schema(description = "id", example = "게시판 id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "제목", example = "강아지를 찾아요", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "제목은 필수 값입니다.")
    private String title;

    @Schema(description = "강아지 url 사진", example = "[https://happyimage.co/1]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> imageUrlList;

    @Schema(description = "이름", example = "홍길동", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(description = "연락 수단", example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "주인 연락처는 필수 값입니다.")
    private String contact;

    @Schema(description = "구체적인 실종 장소", example = "삼성동 코엑스 근처", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "구체적인 실종 장소는 필수 값입니다.")
    private String specificLocation;

    @Schema(description = "위도", example = "37.413294", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "위도는 필수 값입니다.")
    private Double latitude;

    @Schema(description = "경도", example = "126.734086", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "경도는 필수 값입니다.")
    private Double longitude;

    @Schema(description = "실종 일시", example = "2023-08-03T10:15", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "실종 일시는 필수 값입니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime lostDateTime;

    @Schema(description = "실종 경위", example = "산책 중 줄을 놓치게 되어 잃어버렸습니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "실종 상황은 필수 값입니다.")
    private String situation;

    @Schema(description = "강아지 성별", example = "MALE, FEMALE, NOT_SURE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "강아지 성별 입력은 필수 값 입니다.")
    private Gender petGender;
    @Schema(description = "강아지 중성화 여부", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isNeutered;
    @Schema(description = "강아지 품종", example = "시베리안 허스키", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String petBreed;
    @Schema(description = "강아지 생일", example = "2023-08-03", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @Schema(description = "강아지 외형 설명", example = "사람을 잘 따르고 배에 상처가 있어요.",  requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "강아지 설명은 필수 값입니다.")
    private String petDescription;

    @Schema(description = "기타", example = "며칠 동안 이 근처를 돌아다녔어요", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "작성자", example = "ba12839", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "작성자는 필수 값입니다.")
    private String userId;

    @Schema(description = "나이", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    public int getAge() {
        if (this.birthDate == null) {
            return 0;
        }
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
}
