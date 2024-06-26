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

    // MongoDB 저장소와 OAuth2 클라이언트 등록 정보를 관리하는 리포지토리
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;

    /**
     * 지정된 클라이언트 등록 ID 및 사용자 이름을 기반으로 OAuth2AuthorizedClient를 로드합니다.
     *
     * @param clientRegistrationId 클라이언트 등록 ID
     * @param principalName 사용자 이름
     * @param <T> OAuth2AuthorizedClient의 타입
     * @return 로드된 OAuth2AuthorizedClient, 없으면 null
     */
    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        // MongoDB에서 클라이언트 정보를 검색
        Optional<OAuth2AuthorizedClientEntity> clientEntity = Optional.ofNullable(authorizedClientRepository.findByClientRegistrationIdAndPrincipalName(clientRegistrationId, principalName));
        if (clientEntity.isPresent()) {
            OAuth2AuthorizedClientEntity entity = clientEntity.get();
            // 클라이언트 등록 정보를 로드
            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
            if (clientRegistration != null) {
                // OAuth2AuthorizedClient 객체 생성
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

    /**
     * OAuth2AuthorizedClient를 저장합니다.
     *
     * @param authorizedClient 저장할 OAuth2AuthorizedClient
     * @param principal 사용자 인증 정보
     */
    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        // OAuth2AuthorizedClientEntity 객체를 생성하고 MongoDB에 저장
        OAuth2AuthorizedClientEntity entity = OAuth2AuthorizedClientEntity.builder()
                .clientRegistrationId(authorizedClient.getClientRegistration().getRegistrationId())
                .principalName(principal.getName())
                .accessToken(authorizedClient.getAccessToken())
                .refreshToken(authorizedClient.getRefreshToken())
                .clientRegistration(authorizedClient.getClientRegistration())
                .build();
        authorizedClientRepository.save(entity);
    }

    /**
     * 지정된 클라이언트 등록 ID 및 사용자 이름을 기반으로 OAuth2AuthorizedClient를 삭제합니다.
     *
     * @param clientRegistrationId 클라이언트 등록 ID
     * @param principalName 사용자 이름
     */
    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        // MongoDB에서 OAuth2 클라이언트 정보 삭제
        authorizedClientRepository.deleteByClientRegistrationIdAndPrincipalName(clientRegistrationId, principalName);
    }
}
