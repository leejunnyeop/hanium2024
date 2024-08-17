package com.example.mypet.pet.domain;

import com.example.mypet.pet.domain.dto.PetRequestDto;
import com.example.mypet.pet.domain.dto.PetResponseDto;
import com.example.mypet.pet.domain.entity.Pets;
import com.example.mypet.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class PetMapper {

    private final S3Service s3Service;

    // PetRequestDto를 Pets 엔티티로 변환
    public Pets toPetEntity(PetRequestDto petDto) throws IOException {
        return Pets.builder()
                .name(petDto.getName())
                .gender(petDto.getGender())
                .birthDate(petDto.getBirthDate())
                .breed(petDto.getBreed())
                .neutered(petDto.getNeutered())
                .imageUrl(s3Service.upload(petDto.getBase64Image()))
                .noseImgUrl(petDto.getNoseImgUrl())
                .build();
    }


    // Pets 엔티티를 PetResponseDto로 변환
    public PetResponseDto toPetResponseDto(Pets pets) {
        return PetResponseDto.builder()
                .name(pets.getName())
                .gender(pets.getGender())
                .birthDate(pets.getBirthDate())
                .breed(pets.getBreed())
                .description(pets.getDescription())
                .neutered(pets.getNeutered())
                .imageUrl(pets.getImageUrl())
                .appearance(pets.getAppearance())
                .noseImgUrl(pets.getNoseImgUrl())
                .build();
    }

}
