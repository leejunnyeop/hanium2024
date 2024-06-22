package com.example.mypet.security.service.oauthSerivce;


import com.example.mypet.security.domain.oauth.OAuth2AuthorizedClientEntity;
import com.example.mypet.security.repository.OAuth2AuthorizedClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Optional<OAuth2AuthorizedClientEntity> clientEntity = Optional.ofNullable(authorizedClientRepository.findByClientRegistrationIdAndPrincipalName(clientRegistrationId, principalName));
        if (clientEntity.isPresent()) {
            OAuth2AuthorizedClientEntity entity = clientEntity.get();
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
            if (clientRegistration != null) {
                OAuth2AuthorizedClient client = new OAuth2AuthorizedClient(
                        clientRegistration,
                        principalName,
                        entity.getAccessToken(),
                        entity.getRefreshToken()
                );
                return (T) client;
            }
        }
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        OAuth2AuthorizedClientEntity entity = OAuth2AuthorizedClientEntity.builder()
                .clientRegistrationId(authorizedClient.getClientRegistration().getRegistrationId())
                .principalName(principal.getName())
                .accessToken(authorizedClient.getAccessToken())
                .refreshToken(authorizedClient.getRefreshToken())
                .clientRegistration(authorizedClient.getClientRegistration())
                .build();
        authorizedClientRepository.save(entity);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        authorizedClientRepository.deleteByClientRegistrationIdAndPrincipalName(clientRegistrationId, principalName);
    }
}
