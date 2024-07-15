package com.example.mypet.pet.domain;

import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.pet.domain.entity.Pet;

public class PetMapper {

    public static Pet toEntity(PetDto petDto) {
        return Pet.builder()
                .name(petDto.getName())
                .gender(petDto.getGender())
                .birthDate(petDto.getBirthDate())
                .breed(petDto.getBreed())
                .description(petDto.getDescription())
                .neutered(petDto.isNeutered())
                .imageUrl(petDto.getImageUrl())
                .build();
    }

    public static PetDto toDto(Pet pet) {
        return PetDto.builder()
                .name(pet.getName())
                .gender(pet.getGender())
                .birthDate(pet.getBirthDate())
                .breed(pet.getBreed())
                .description(pet.getDescription())
                .neutered(pet.isNeutered())
                .imageUrl(pet.getImageUrl())
                .build();
    }


}
