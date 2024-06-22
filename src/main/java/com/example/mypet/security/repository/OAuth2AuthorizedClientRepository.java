package com.example.mypet.security.repository;

import com.example.mypet.security.domain.oauth.OAuth2AuthorizedClientEntity;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface OAuth2AuthorizedClientRepository extends MongoRepository<OAuth2AuthorizedClientEntity, String> {

    void deleteByClientRegistrationIdAndPrincipalName(String clientRegistrationId, String principalName);
    OAuth2AuthorizedClientEntity findByClientRegistrationIdAndPrincipalName(String clientRegistrationId, String principalName);
}
