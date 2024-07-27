package com.example.mypet.find.domain;

import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.pet.domain.entity.Pets;

public class LostAndFoundPetsBoardMapper {

    public static LostAndFoundPetsBoardResponseDto toPetLostBoardResponseDto(LostAndFoundPetsBoard entity, Pets pet) {
        return LostAndFoundPetsBoardResponseDto.builder()
                .id(entity.getId())
                .imageUrl(pet.getImageUrl())
                .location(entity.getLocation())
                .dateTime(entity.getMissingDateTime())
                .breed(pet.getBreed())
                .build();
    }

    public static LostAndFoundPetsBoardResponseDto toOwnerFoundBoardResponseDto(LostAndFoundPetsBoard entity) {
        return LostAndFoundPetsBoardResponseDto.builder()
                .id(entity.getId())
                .location(entity.getLocation())
                .dateTime(entity.getMissingDateTime())
                .build();
    }

    public static LostAndFoundPetsBoardDetailResponseDto toPetLostBoardDetailResponseDto(LostAndFoundPetsBoard entity, Pets pet) {
        return LostAndFoundPetsBoardDetailResponseDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .imageUrl(pet.getImageUrl())
                .location(entity.getLocation())
                .dateTime(entity.getMissingDateTime())
                .reporterName(entity.getReporterName())
                .reporterContact(entity.getReporterContact())
                .postType(entity.getPostType())
                .breed(pet.getBreed())
                .gender(pet.getGender())
                .age(pet.getAge())
                .description(pet.getDescription())
                .hasNoseImg(pet.getHasNoseImg())
                .build();
    }

    public static LostAndFoundPetsBoardDetailResponseDto toOwnerFoundBoardDetailResponseDto(LostAndFoundPetsBoard entity) {
        return LostAndFoundPetsBoardDetailResponseDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .location(entity.getLocation())
                .dateTime(entity.getMissingDateTime())
                .reporterName(entity.getReporterName())
                .reporterContact(entity.getReporterContact())
                .postType(entity.getPostType())
                .build();
    }
}
