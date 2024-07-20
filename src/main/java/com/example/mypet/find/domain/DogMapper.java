package com.example.mypet.find.domain;

import com.example.mypet.find.domain.entity.LostAndFoundPetsBoard;
import com.example.mypet.find.domain.request.DogFinderRequest;
import com.example.mypet.find.domain.response.DogFinderDetailsResponse;
import com.example.mypet.find.domain.response.DogFinderResponse;

public class DogMapper {

    public static LostAndFoundPetsBoard toLostAndFoundPetsBoard(DogFinderRequest dogFinderRequest){
        return LostAndFoundPetsBoard.builder()
                .name(dogFinderRequest.getName())
                .exterior(dogFinderRequest.getExterior())
                .imgUrl(dogFinderRequest.getImgUrl())
                .stats(dogFinderRequest.getStats())
                .phoneNumber(dogFinderRequest.getPhoneNumber())
                .build();
    }

    public static DogFinderResponse toDogFinderResponse(LostAndFoundPetsBoard lostAndFoundPetsBoard){
        return DogFinderResponse.builder()
                .name(lostAndFoundPetsBoard.getName())
                .exterior(lostAndFoundPetsBoard.getExterior())
                .imgUrl(lostAndFoundPetsBoard.getImgUrl())
                .stats(lostAndFoundPetsBoard.getStats())
                .build();
    }

    public static DogFinderDetailsResponse toDogFinderDetailsResponse(LostAndFoundPetsBoard lostAndFoundPetsBoard){
        return DogFinderDetailsResponse.builder()
                .name(lostAndFoundPetsBoard.getName())
                .exterior(lostAndFoundPetsBoard.getExterior())
                .imgUrl(lostAndFoundPetsBoard.getImgUrl())
                .stats(lostAndFoundPetsBoard.getStats())
                .phoneNumber(lostAndFoundPetsBoard.getPhoneNumber())
                .build();
    }
}
