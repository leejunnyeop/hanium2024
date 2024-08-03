package com.example.mypet.pet.domain;

import com.example.mypet.pet.domain.dto.PetRequestDto;
import com.example.mypet.pet.domain.dto.PetResponseDto;
import com.example.mypet.pet.domain.entity.Pets;

public class PetMapper {

    // PetRequestDto를 Pets 엔티티로 변환
    public static Pets toPetEntity(PetRequestDto petRequestDto) {
        return Pets.builder()
                .id(petRequestDto.getId())
                .name(petRequestDto.getName())
                .gender(petRequestDto.getGender())
                .birthDate(petRequestDto.getBirthDate())
                .breed(petRequestDto.getBreed())
                .description(petRequestDto.getDescription())
                .neutered(petRequestDto.getNeutered())
                .imageUrl(petRequestDto.getImageUrl())
                .build();
    }

    // Pets 엔티티를 PetRequestDto로 변환
    public static PetRequestDto toPetRequestDto(Pets pets) {
        return PetRequestDto.builder()
                .id(pets.getId())
                .name(pets.getName())
                .gender(pets.getGender())
                .birthDate(pets.getBirthDate())
                .breed(pets.getBreed())
                .description(pets.getDescription())
                .neutered(pets.getNeutered())
                .imageUrl(pets.getImageUrl())
                .build();
    }

    // Pets 엔티티를 PetResponseDto로 변환
    public static PetResponseDto toPetResponseDto(Pets pets) {
        return PetResponseDto.builder()
                .name(pets.getName())
                .birthDate(pets.getBirthDate())
                .breed(pets.getBreed())
                .description(pets.getDescription())
                .build();
    }

}
