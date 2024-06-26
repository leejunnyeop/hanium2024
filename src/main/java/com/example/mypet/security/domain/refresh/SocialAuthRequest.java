package com.example.mypet.security.domain.refresh;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SocialAuthRequest {

    private String provider;
    private String code;
    private String codeVerifier;

}
