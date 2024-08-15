package com.example.mypet.ownerSearchBoard;

import com.example.mypet.security.domain.users.Users;
import com.example.mypet.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OwnerSearchBoardMapper {
    private final S3Service s3Service;


    public OwnerSearchBoard toOwnerSearchBoard(OwnerSearchBoardRequestDto requestDto, Users user) throws IOException {
        List<String> imageUrlList = s3Service.upload(requestDto.getBase64ImageList());
        return OwnerSearchBoard.builder()
                .title(requestDto.getTitle())
                .imageUrlList(imageUrlList)
                .name(requestDto.getName())
                .reporterContact(requestDto.getReporterContact())
                .specificLocation(requestDto.getSpecificLocation())
                .longitude(requestDto.getLongitude())
                .latitude(requestDto.getLatitude())
                .foundDateTime(requestDto.getFoundDateTime())
                .situation(requestDto.getSituation())
                .petGender(requestDto.getPetGender())
                .petBreed(requestDto.getPetBreed())
                .petDescription(requestDto.getPetDescription())
                .content(requestDto.getContent())
                .user(user)
                .build();
    }

    public OwnerSearchBoardResponseDto toOwnerSearchBoardResponseDto(OwnerSearchBoard ownerSearchBoard) {
        return OwnerSearchBoardResponseDto.builder()
                .id(ownerSearchBoard.getId())
                .title(ownerSearchBoard.getTitle())
                .imageUrlList(ownerSearchBoard.getImageUrlList())
                .name(ownerSearchBoard.getName())
                .reporterContact(ownerSearchBoard.getReporterContact())
                .specificLocation(ownerSearchBoard.getSpecificLocation())
                .longitude(ownerSearchBoard.getLongitude())
                .latitude(ownerSearchBoard.getLatitude())
                .foundDateTime(ownerSearchBoard.getFoundDateTime())
                .situation(ownerSearchBoard.getSituation())
                .petGender(ownerSearchBoard.getPetGender())
                .petBreed(ownerSearchBoard.getPetBreed())
                .petDescription(ownerSearchBoard.getPetDescription())
                .content(ownerSearchBoard.getContent())
                .build();
    }

}
