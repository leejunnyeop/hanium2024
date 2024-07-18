package com.example.mypet.pet.domain.entity;

import com.example.mypet.pet.domain.Gender;
import com.example.mypet.pet.domain.dto.PetDto;
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
    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String breed;
    private String description;
    private Boolean neutered;  // 중성화 여부 추가
    private String imageUrl;   // 이미지 URL 추가

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
