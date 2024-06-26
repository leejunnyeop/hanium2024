package com.example.mypet.security.domain.oauth;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

/**
 * MongoDB에 저장되는 OAuth2AuthorizedClient 정보를 담는 엔티티 클래스.
 */
@Document(collection = "oauth2AuthorizedClients")
@Builder
@Getter
public class OAuth2AuthorizedClientEntity {

    /**
     * MongoDB 문서의 ID 필드.
     */
    @Id
    private String id;

    /**
     * 클라이언트 등록 ID.
     * 예: google, naver 등
     */
    private String clientRegistrationId;

    /**
     * 사용자 이름.
     */
    private String principalName;

    /**
     * OAuth2 액세스 토큰.
     */
    private OAuth2AccessToken accessToken;

    /**
     * OAuth2 리프레시 토큰.
     */
    private OAuth2RefreshToken refreshToken;

    /**
     * OAuth2 클라이언트 등록 정보.
     */
    private ClientRegistration clientRegistration;

}
