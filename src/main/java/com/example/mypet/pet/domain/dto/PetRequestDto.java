package com.example.mypet.pet.domain.dto;

import com.example.mypet.pet.domain.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDto {


    @Schema(description = "반려동물 ID", example = "123456")
    private String id;

    @Schema(description = "반려동물 이름", example = "코코")
    @NotNull(message = "반려동물 이름은 필수 값입니다.")
    private String name;

    @Schema(description = "반려동물 성별", example = "MALE")
    @NotNull(message = "반려동물 성별은 필수 값입니다.")
    private Gender gender;

    @Schema(description = "반려동물 생년월일", example = "2020-01-01")
    @NotNull(message = "반려동물 생년월일은 필수 값입니다.")
    private LocalDate birthDate;

    @Schema(description = "반려동물 품종", example = "시베리안 허스키")
    @NotNull(message = "반려동물 품종은 필수 값입니다.")
    private String breed;

    @Schema(description = "반려동물 설명", example = "활발하고 친근한 강아지")
    @NotNull(message = "반려동물 설명은 필수 값입니다.")
    private String description;

    @Schema(description = "중성화 여부", example = "true")
    private Boolean neutered;

    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
    private String imageUrl;
}
