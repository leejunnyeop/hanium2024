package com.example.mypet.ownerSearchBoard;

import com.example.mypet.pet.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class OwnerSearchBoardRequestDto {

    @Schema(description = "제목", example = "주인을 찾아요", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "제목은 필수 값입니다.")
    private String title;

    @Schema(description = "강아지 사진, base64로 된 이미지", example = "[]", requiredMode = Schema.RequiredMode.REQUIRED, type = "array")
    private List<String> base64ImageList;

    @Schema(description = "이름", example = "홍길동", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String name;

    @Schema(description = "연락 수단", example = "010-1234-5678", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "신고자 연락처는 필수 값입니다.")
    private String reporterContact;

    @Schema(description = "구체적인 발견 장소", example = "삼성동 코엑스 근처", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "구체적인 발견 장소는 필수 값입니다.")
    private String specificLocation;

    @Schema(description = "위도", example = "37.413294", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "위도는 필수 값입니다.")
    private Double latitude;

    @Schema(description = "경도", example = "126.734086", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "경도는 필수 값입니다.")
    private Double longitude;


    @Schema(description = "발견 일시", example = "2023-08-03 10:15", requiredMode = Schema.RequiredMode.REQUIRED, type = "string")
    @NotNull(message = "발견 일시는 필수 값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime foundDateTime;

    @Schema(description = "발견 경위", example = "산책 중 발견했습니다.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "발견 상황은 필수 값입니다.")
    private String situation;

    @Schema(description = "강아지 성별", example = "MALE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "강아지 성별 입력은 필수 값 입니다.")
    private Gender petGender;

    @Schema(description = "강아지 품종", example = "시베리안 허스키", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String petBreed;

    @Schema(description = "강아지 외형 설명", example = "사람을 잘 따르고 배에 상처가 있어요.",  requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "강아지 설명은 필수 값입니다.")
    private String petDescription;

    @Schema(description = "기타", example = "며칠 동안 이 근처를 돌아다녔어요", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;
}