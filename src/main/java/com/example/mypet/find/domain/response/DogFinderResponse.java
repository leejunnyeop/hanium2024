package com.example.mypet.find.domain.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DogFinderResponse {

    private String name;

    private String stats;

    private String exterior;

    private String imgUrl;
}
