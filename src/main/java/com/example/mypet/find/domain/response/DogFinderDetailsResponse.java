package com.example.mypet.find.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DogFinderDetailsResponse {

    private String name;

    private String stats;

    private String exterior;

    private String phoneNumber;

    private String imgUrl;
}
