package com.example.mypet.security.domain;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{


    private final Map <String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getEmail() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("reponse");
        return (String) response.get("email");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return (String) response.get("name");
    }

    @Override
    public String getProvider() {
        return "naver";
    }
}
