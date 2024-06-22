package com.example.mypet.security.domain.oauth;

import lombok.Builder;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

@Document(collection = "oauth2AuthorizedClients")
@Builder
@Getter
public class OAuth2AuthorizedClientEntity {

    @Id
    private String id;
    private String clientRegistrationId;
    private String principalName;
    private OAuth2AccessToken accessToken;
    private OAuth2RefreshToken refreshToken;
    private ClientRegistration clientRegistration;

}
