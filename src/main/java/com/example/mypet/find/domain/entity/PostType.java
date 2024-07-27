package com.example.mypet.find.domain.entity;

public enum PostType {

    OWNER("이 주인을 찾습니다"),
    PET("강아지를 찾습니다");

    private final String description;

    PostType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
