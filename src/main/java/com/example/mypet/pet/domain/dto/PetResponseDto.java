package com.example.mypet.pet.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {

    @Schema(description = "반려동물 이름", example = "코코")
    private String name;

    @Schema(description = "반려동물 생년월일", example = "2020-01-01")
    private LocalDate birthDate;

    @Schema(description = "반려동물 품종", example = "시베리안 허스키")
    private String breed;

    @Schema(description = "반려동물 설명", example = "활발하고 친근한 강아지")
    private String description;
}
