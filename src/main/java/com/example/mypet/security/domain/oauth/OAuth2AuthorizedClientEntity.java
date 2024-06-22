package com.example.mypet.security.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

@Getter
@Builder
@Document(collection = "oauth2AuthorizedClients")
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2AuthorizedClientEntity  {

    @Id
    private String id;
    private String clientRegistrationId;
    private String principalName;
    private OAuth2AccessToken accessToken;
    private OAuth2RefreshToken refreshToken;
    private ClientRegistration clientRegistration;




}
