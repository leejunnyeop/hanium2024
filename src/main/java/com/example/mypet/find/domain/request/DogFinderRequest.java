package com.example.mypet.find.domain.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DogFinderRequest {

    private String name;

    private String stats;

    private String exterior;

    private String phoneNumber;

    private String imgUrl;
}
