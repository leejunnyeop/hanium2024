package com.example.mypet.find.domain;

import com.example.mypet.find.domain.dto.request.OwnerFoundBoardRequestDto;
import com.example.mypet.find.domain.dto.request.PetLostBoardRequestDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardDetailResponseDto;
import com.example.mypet.find.domain.dto.response.LostAndFoundPetsBoardResponseDto;
import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.entity.PostType;
import com.example.mypet.pet.domain.entity.Pets;

public class LostAndFoundPetsBoardMapper {

    public static LostAndFoundPetsBoard toOwnerFoundBoard(OwnerFoundBoardRequestDto requestDto, String userId) {
        return LostAndFoundPetsBoard.builder()
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .city(requestDto.getCity())
                .district(requestDto.getDistrict())
                .specificLocation(requestDto.getSpecificLocation())
                .missingDateTime(requestDto.getFoundDateTime())
                .missingSituation(requestDto.getSituation())
                .reporterName(requestDto.getReporterName())
                .reporterContact(requestDto.getReporterContact())
                .postType(PostType.OWNER)
                .petName(requestDto.getPetName())
                .petGender(requestDto.getPetGender())
                .petBreed(requestDto.getPetBreed())
                .petDescription(requestDto.getPetDescription())
                .petNeutered(requestDto.getPetNeutered())
                .petImageUrl(requestDto.getPetImageUrl())
                .build();
    }

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

    public static LostAndFoundPetsBoardDetailResponseDto toLostAndFoundPetsBoardDetailResponseDto(LostAndFoundPetsBoard entity) {
        return LostAndFoundPetsBoardDetailResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .imageUrl(entity.getPetImageUrl())
                .location(entity.getCity() + " " + entity.getDistrict() + " " + entity.getSpecificLocation())
                .dateTime(entity.getMissingDateTime())
                .reporterName(entity.getReporterName())
                .reporterContact(entity.getReporterContact())
                .postType(entity.getPostType())
                .breed(entity.getPetBreed())
                .gender(entity.getPetGender())
                .description(entity.getPetDescription())
                .age(entity.getAge())
                .petDescription(entity.getPetDescription())
                .build();
    }

    public static LostAndFoundPetsBoardDetailResponseDto toOwnerFoundBoardDetailResponseDto(LostAndFoundPetsBoard entity) {
        return LostAndFoundPetsBoardDetailResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .imageUrl(entity.getPetImageUrl())
                .location(entity.getCity() + " " + entity.getDistrict() + " " + entity.getSpecificLocation())
                .dateTime(entity.getMissingDateTime())
                .reporterName(entity.getReporterName())
                .reporterContact(entity.getReporterContact())
                .postType(entity.getPostType())
                .breed(entity.getPetBreed())
                .gender(entity.getPetGender())
                .description(entity.getPetDescription())
                .age(entity.getAge())
                .petDescription(entity.getPetDescription())
                .build();
    }

    public static LostAndFoundPetsBoard toLostAndFoundPetsBoardAuto(PetLostBoardRequestDto requestDto, Pets pet) {
        return LostAndFoundPetsBoard.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .city(requestDto.getCity())
                .district(requestDto.getDistrict())
                .specificLocation(requestDto.getSpecificLocation())
                .missingDateTime(requestDto.getMissingDateTime())
                .missingSituation(requestDto.getMissingSituation())
                .reporterName(requestDto.getReporterName())
                .reporterContact(requestDto.getReporterContact())
                .postType(PostType.PET)
                .petName(pet.getName()) // 강아지 정보 설정
                .petGender(pet.getGender())
                .petBirthDate(pet.getBirthDate())
                .petBreed(pet.getBreed())
                .petDescription(pet.getDescription())
                .petNeutered(pet.getNeutered())
                .petImageUrl(pet.getImageUrl())
                .build();
    }

    public static LostAndFoundPetsBoard toLostAndFoundPetsBoardManual(PetLostBoardRequestDto requestDto) {
        return LostAndFoundPetsBoard.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .city(requestDto.getCity())
                .district(requestDto.getDistrict())
                .specificLocation(requestDto.getSpecificLocation())
                .missingDateTime(requestDto.getMissingDateTime())
                .missingSituation(requestDto.getMissingSituation())
                .reporterName(requestDto.getReporterName())
                .reporterContact(requestDto.getReporterContact())
                .postType(PostType.PET)
                .petName(requestDto.getPetName()) // 직접 입력한 강아지 정보 설정
                .petGender(requestDto.getPetGender())
                .petBirthDate(requestDto.getPetBirthDate())
                .petBreed(requestDto.getPetBreed())
                .petDescription(requestDto.getPetDescription())
                .petNeutered(requestDto.getPetNeutered())
                .petImageUrl(requestDto.getPetImageUrl())
                .build();
    }

    public static LostAndFoundPetsBoard toOwnerFoundBoard(OwnerFoundBoardRequestDto requestDto) {
        return LostAndFoundPetsBoard.builder()
                .content(requestDto.getContent())
                .title(requestDto.getTitle())
                .city(requestDto.getCity())
                .district(requestDto.getDistrict())
                .specificLocation(requestDto.getSpecificLocation())
                .missingDateTime(requestDto.getFoundDateTime())
                .missingSituation(requestDto.getSituation())
                .reporterName(requestDto.getReporterName())
                .reporterContact(requestDto.getReporterContact())
                .postType(PostType.OWNER)
                .petName(requestDto.getPetName())
                .petGender(requestDto.getPetGender())
                .petBreed(requestDto.getPetBreed())
                .petDescription(requestDto.getPetDescription())
                .petNeutered(requestDto.getPetNeutered())
                .petImageUrl(requestDto.getPetImageUrl())
                .build();
    }



}
