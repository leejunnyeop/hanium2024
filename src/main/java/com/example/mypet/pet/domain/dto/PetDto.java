package com.example.mypet.pet.domain.dto;

import com.example.mypet.pet.domain.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {

    private String name;
    private Gender gender;
    private LocalDate birthDate;
    private String breed;
    private String description;
    private boolean neutered;
    private String imageUrl;


}
