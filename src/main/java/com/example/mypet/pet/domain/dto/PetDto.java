package com.example.pet.domain.dto;

import com.example.pet.domain.Gender;
import com.example.pet.domain.entity.Pet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
public class PetDto {

    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String breed;
    private String description;
    private boolean neutered;
    private String imageUrl;


}
