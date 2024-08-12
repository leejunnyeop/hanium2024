package com.example.mypet.pet.domain.dto;

import com.example.mypet.pet.domain.Gender;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetRequestDto {


    @Schema(description = "반려동물 ID", example = "123456")
    private String id;

    @Schema(description = "반려동물 이름", example = "코코")
    private String name;

    @Schema(description = "반려동물 성별", example = "MALE")
    private Gender gender;

    @Schema(description = "반려동물 생년월일", example = "2020-01-01")
    private LocalDate birthDate;

    @Schema(description = "반려동물 품종", example = "시베리안 허스키")
    private String breed;

    @Schema(description = "중성화 여부", example = "true")
    private Boolean neutered;

    @Schema(description = "base64Image", example = "/abaseasffa/")
    private String base64Image;

    @Setter
    @Schema(description = "이미지 URL", example = "http://example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "비문 데이터 URL", example = "['http://example.com/image.jpg', 'http://example.com/image.jpg', 'http://example.com/image.jpg', ...]")
    private List<String> noseImgUrl;
}
