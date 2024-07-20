package com.example.mypet.pet.domain.entity;


import com.example.mypet.pet.domain.Gender;
import com.example.mypet.pet.domain.dto.PetDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "pets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pets {

    @Id
    private String id;

    @Schema(description = "강아지이름", example = "퍼피")
    private String name;

    @Schema(description = "성별", example = "MALE" +"or" + "FEMALE")
    private Gender gender;

    @Schema(description = "생일", example = "2024-05-05")
    private LocalDate birthDate;

    @Schema(description = "강아 종", example = "진도개")
    private String breed;

    @Schema(description = "소개(자랑)", example = "멋쟁이이예요")
    private String description;

    @Schema(description = "중성화여부", example = "True" +"or" + "False")
    private Boolean neutered;  // 중성화 여부 추가


    private String imageUrl;   // 이미지 URL 추가


    private String noseImgUrl;

    public void updateFromDto(PetDto petDto) {
        if (petDto.getName() != null) {
            this.name = petDto.getName();
        }
        if (petDto.getGender() != null) {
            this.gender = petDto.getGender();
        }
        if (petDto.getBirthDate() != null) {
            this.birthDate = petDto.getBirthDate();
        }
        if (petDto.getBreed() != null) {
            this.breed = petDto.getBreed();
        }
        if (petDto.getDescription() != null) {
            this.description = petDto.getDescription();
        }
        if (petDto.getImageUrl() != null) {
            this.imageUrl = petDto.getImageUrl();
        }
        this.neutered = petDto.getNeutered();
    }


}
