package com.example.mypet.pet.domain;

import com.example.mypet.pet.domain.dto.PetDto;
import com.example.mypet.pet.domain.entity.Pets;

public class PetMapper {

    public static Pets toEntity(PetDto petDto) {
        return Pets.builder()
                .id(petDto.getId())
                .name(petDto.getName())
                .gender(petDto.getGender())
                .birthDate(petDto.getBirthDate())
                .breed(petDto.getBreed())
                .description(petDto.getDescription())
                .neutered(petDto.getNeutered())
                .imageUrl(petDto.getImageUrl())
                .noseImgUrl(petDto.getNoseImgUrl())
                .build();
    }

    public static PetDto toDto(Pets pets) {
        return PetDto.builder()
                .id(pets.getId())
                .name(pets.getName())
                .gender(pets.getGender())
                .birthDate(pets.getBirthDate())
                .breed(pets.getBreed())
                .description(pets.getDescription())
                .neutered(pets.getNeutered())
                .imageUrl(pets.getImageUrl())
                .noseImgUrl(pets.getNoseImgUrl())
                .build();
    }


}
