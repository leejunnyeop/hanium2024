package com.example.mypet.pet.domain.entity;

import com.example.mypet.pet.domain.Gender;
import com.example.mypet.pet.domain.dto.PetRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pets {

//    @Schema(description = "반려동물 ID", example = "123456")
//    private String id;

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

    private List<String> noseImgUrl;


    public boolean getHasNoseImg() {
        return this.noseImgUrl != null;
    }

    public int getAge() {
        if (this.birthDate == null) {
            return 0;
        }
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }

    public void updateFromDto(PetRequestDto petRequestDto) {
        if (petRequestDto.getName() != null) {
            this.name = petRequestDto.getName();
        }
        if (petRequestDto.getGender() != null) {
            this.gender = petRequestDto.getGender();
        }
        if (petRequestDto.getBirthDate() != null) {
            this.birthDate = petRequestDto.getBirthDate();
        }
        if (petRequestDto.getBreed() != null) {
            this.breed = petRequestDto.getBreed();
        }
        this.neutered = petRequestDto.getNeutered();
    }


}
