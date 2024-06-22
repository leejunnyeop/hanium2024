package com.example.mypet.security.service.oauthSerivce;

import com.example.mypet.security.domain.oauth.OAuth2AuthorizedClientEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

import java.util.Optional;


public class MongoOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public MongoOAuth2AuthorizedClientService(OAuth2AuthorizedClientRepository authorizedClientRepository,
                                              ClientRegistrationRepository clientRegistrationRepository) {
        this.authorizedClientRepository = authorizedClientRepository;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        Optional<OAuth2AuthorizedClientEntity> clientEntity = authorizedClientRepository.findByClientRegistrationIdAndPrincipalName(clientRegistrationId, principalName);
        if (clientEntity.isPresent()) {
            OAuth2AuthorizedClientEntity entity = clientEntity.get();
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
            if (clientRegistration != null) {
                return (T) new OAuth2AuthorizedClient(clientRegistration, entity.getPrincipalName(), entity.getAccessToken(), entity.getRefreshToken());
            }
        }
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {

    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, String principalName) {
        OAuth2AuthorizedClientEntity entity = new OAuth2AuthorizedClientEntity();
        entity.setClientRegistrationId(authorizedClient.getClientRegistrationId());
        entity.setPrincipalName(principalName);
        entity.setAccessToken(authorizedClient.getAccessToken());
        entity.setRefreshToken(authorizedClient.getRefreshToken());
        authorizedClientRepository.save(entity);
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        authorizedClientRepository.deleteByClientRegistrationIdAndPrincipalName(clientRegistrationId, principalName);
    }
}
