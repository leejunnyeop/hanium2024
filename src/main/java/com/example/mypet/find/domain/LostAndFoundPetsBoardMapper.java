package com.example.mypet.find.domain;

import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;

public class LostAndFoundPetsBoardMapper {



    public static LostAndFoundPetsBoardResponseDto toOwnerFoundBoardResponseDto(LostAndFoundPetsBoard entity) {
        return LostAndFoundPetsBoardResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .imageUrl(entity.getPetImageUrl())
                .location(entity.getCity() + " " + entity.getDistrict() + " " + entity.getSpecificLocation())
                .dateTime(entity.getMissingDateTime())
                .breed(entity.getPetBreed())
                .gender(entity.getPetGender())
                .petDescription(entity.getPetDescription())
                .build();
    }

    public static LostAndFoundPetsBoardResponseDto toPetLostBoardResponseDto(LostAndFoundPetsBoard entity) {
        return LostAndFoundPetsBoardResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .imageUrl(entity.getPetImageUrl())
                .location(entity.getCity() + " " + entity.getDistrict() + " " + entity.getSpecificLocation())
                .dateTime(entity.getMissingDateTime())
                .breed(entity.getPetBreed())
                .gender(entity.getPetGender())
                .petDescription(entity.getPetDescription())
                .build();
    }




}
