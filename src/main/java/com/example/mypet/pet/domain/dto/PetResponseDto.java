package com.example.mypet.pet.domain.dto;

import com.example.mypet.pet.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetResponseDto {

    @Schema(description = "반려동물 이름", example = "코코")
    private String name;

    @Schema(description = "반려동물 성별", example = "MALE")
    private Gender gender;

    @Schema(description = "반려동물 생년월일", example = "2020-01-01")
    private LocalDate birthDate;

    @Schema(description = "반려동물 품종", example = "시베리안 허스키")
    private String breed;

    @Schema(description = "반려동물 설명", example = "활발하고 친근한 강아지")
    private String description;

    @Schema(description = "중성화 여부", example = "true")
    private Boolean neutered;

    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "강아지 외형", example = "털이 부드럽고, 귀가 큰 강아지")
    private String appearance; // 외형 추가

    @Schema(description = "비문 데이터 URL", example = "['http://example.com/image.jpg', 'http://example.com/image.jpg', 'http://example.com/image.jpg']")
    private List<String> noseImgUrl;

    @Schema(description = "반려동물 나이", example = "12")
    public int getAge() {
        if (this.birthDate == null) {
            return 0;
        }
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }
}
