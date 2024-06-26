package com.example.mypet.security.service.tokenService;

import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultTokenService implements TokenService {

    private static final String GOOGLE_CLIENT_ID = "54570271712-a2ct0tbftq1gdrnf7p9pk6m8kfs4sbsl.apps.googleusercontent.com";
    private static final String GOOGLE_CLIENT_SECRET = "YOUR_GOOGLE_CLIENT_SECRET";
    private static final String GOOGLE_REDIRECT_URI = "com.mypet:/oauth2redirect/google";

    private static final String NAVER_CLIENT_ID = "aRtsZ5tiPboW41G3a8iO";
    private static final String NAVER_CLIENT_SECRET = "UKEFaAtLbo";
    private static final String NAVER_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/naver";

    @Override
    public OAuth2AccessTokenResponse getAccessTokenResponse(String provider, String code, String codeVerifier) {
        String tokenUri = getTokenUri(provider);
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<>();
            params.put("code", code);
            params.put("client_id", getClientId(provider));
            params.put("client_secret", getClientSecret(provider));
            params.put("redirect_uri", getRedirectUri(provider));
            params.put("grant_type", "authorization_code");
            params.put("code_verifier", codeVerifier);

            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, params, Map.class);
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("access_token") && responseBody.containsKey("refresh_token")) {
                String accessToken = (String) responseBody.get("access_token");
                String refreshToken = (String) responseBody.get("refresh_token");
                return new OAuth2AccessTokenResponse(accessToken, refreshToken);
            } else {
                throw new IllegalArgumentException("액세스 토큰을 가져오는 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("액세스 토큰을 가져오는 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private String getTokenUri(String provider) {
        switch (provider) {
            case "google":
                return "https://oauth2.googleapis.com/token";
            case "naver":
                return "https://nid.naver.com/oauth2.0/token";
            default:
                throw new IllegalArgumentException("지원되지 않는 제공자입니다: " + provider);
        }
    }

    private String getClientId(String provider) {
        switch (provider) {
            case "google":
                return GOOGLE_CLIENT_ID;
            case "naver":
                return NAVER_CLIENT_ID;
            default:
                throw new IllegalArgumentException("지원되지 않는 제공자입니다: " + provider);
        }
    }

    private String getClientSecret(String provider) {
        switch (provider) {
            case "google":
                return GOOGLE_CLIENT_SECRET;
            case "naver":
                return NAVER_CLIENT_SECRET;
            default:
                throw new IllegalArgumentException("지원되지 않는 제공자입니다: " + provider);
        }
    }

    private String getRedirectUri(String provider) {
        switch (provider) {
            case "google":
                return GOOGLE_REDIRECT_URI;
            case "naver":
                return NAVER_REDIRECT_URI;
            default:
                throw new IllegalArgumentException("지원되지 않는 제공자입니다: " + provider);
        }
    }
}
