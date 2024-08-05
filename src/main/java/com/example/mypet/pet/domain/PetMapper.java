package com.example.mypet.pet.domain;

import com.example.mypet.pet.domain.dto.PetRequestDto;
import com.example.mypet.pet.domain.dto.PetResponseDto;
import com.example.mypet.pet.domain.entity.Pets;

public class PetMapper {

    // PetRequestDto를 Pets 엔티티로 변환
    public static Pets toPetEntity(PetRequestDto petDto) {
        return Pets.builder()
                .id(petDto.getId())
                .name(petDto.getName())
                .gender(petDto.getGender())
                .birthDate(petDto.getBirthDate())
                .breed(petDto.getBreed())
                .neutered(petDto.getNeutered())
                .imageUrl(petDto.getImageUrl())
                .noseImgUrl(petDto.getNoseImgUrl())
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
                .neutered(pets.getNeutered())
                .imageUrl(pets.getImageUrl())
                .noseImgUrl(pets.getNoseImgUrl())
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
