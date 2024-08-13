package com.example.mypet.petSearchBoard;

import com.example.mypet.security.domain.users.Users;
import com.example.mypet.security.repository.UsersRepository;
import com.example.mypet.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class PetSearchBoardMapper {
    private final PetSearchBoardRepository petSearchBoardRepository;
    private final UsersRepository usersRepository;
    private final S3Service s3Service;

    public PetSearchBoard toPetSearchBoard(PetSearchBoardRequestDto requestDto, Users user) throws IOException {
        return PetSearchBoard.builder()
                .title(requestDto.getTitle())
                .imageUrlList(s3Service.upload(requestDto.getBase64ImageList()))
                .name(requestDto.getName())
                .contact(requestDto.getContact())
                .specificLocation(requestDto.getSpecificLocation())
                .lostDateTime(requestDto.getLostDateTime())
                .situation(requestDto.getSituation())
                .petGender(requestDto.getPetGender())
                .isNeutered(requestDto.getIsNeutered())
                .petBreed(requestDto.getPetBreed())
                .birthDate(requestDto.getBirthDate())
                .petDescription(requestDto.getPetDescription())
                .content(requestDto.getContent())
                .user(user)
                .build();
    }

    public PetSearchBoardResponseDto toPetSearchBoardResponseDto(PetSearchBoard petSearchBoard) {
        return PetSearchBoardResponseDto.builder()
                .id(petSearchBoard.getId())
                .title(petSearchBoard.getTitle())
                .imageUrlList(petSearchBoard.getImageUrlList())
                .name(petSearchBoard.getName())
                .contact(petSearchBoard.getContact())
                .specificLocation(petSearchBoard.getSpecificLocation())
                .lostDateTime(petSearchBoard.getLostDateTime())
                .situation(petSearchBoard.getSituation())
                .petGender(petSearchBoard.getPetGender())
                .isNeutered(petSearchBoard.getIsNeutered())
                .petBreed(petSearchBoard.getPetBreed())
                .birthDate(petSearchBoard.getBirthDate())
                .petDescription(petSearchBoard.getPetDescription())
                .content(petSearchBoard.getContent())
                .userId(petSearchBoard.getUser().getId())
                .build();
    }
}
