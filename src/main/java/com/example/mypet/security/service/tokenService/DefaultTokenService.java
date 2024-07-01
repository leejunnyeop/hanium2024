package com.example.mypet.security.service.tokenService;

import com.example.mypet.security.domain.refresh.OAuth2AccessTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class DefaultTokenService implements TokenService {

    private static final String GOOGLE_CLIENT_ID = "54570271712-a2ct0tbftq1gdrnf7p9pk6m8kfs4sbsl.apps.googleusercontent.com";
//    private static final String GOOGLE_CLIENT_SECRET = "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";
    private static final String GOOGLE_REDIRECT_URI = "com.mypet:/oauth2redirect/google";

    private static final String NAVER_CLIENT_ID = "aRtsZ5tiPboW41G3a8iO";
    private static final String NAVER_CLIENT_SECRET = "UKEFaAtLbo";
    private static final String NAVER_REDIRECT_URI = "http://localhost:8080/login/oauth2/code/naver";

    @Override
    public OAuth2AccessTokenResponse getAccessTokenResponse(String provider, String code, String codeVerifier) {
        String tokenUri = getTokenUri(provider);
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
//            System.out.println("Decoded Code: " + decodedCode);

            Map<String, String> params = new HashMap<>();
            params.put("code", decodedCode);
            params.put("client_id", getClientId(provider));
//            params.add("client_secret", getClientSecret(provider));
//            params.put("redirect_uri", getRedirectUri(provider));
            params.put("grant_type", "authorization_code");

            params.put("code_verifier", codeVerifier);

            System.out.println("Token URI: " + tokenUri);
            System.out.println("Request Params: " + params);
            System.out.println(params.get("code"));
//            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);
//            System.out.println(requestEntity.getBody());
//            System.out.println(requestEntity.getHeaders());
            System.out.println("Sending request...");

            // HTTP POST 요청 보내기
            ResponseEntity<String> response = restTemplate.postForEntity(
                    tokenUri,
                    params,
                    String.class
            );

            log.info("Response received");

            // 응답 처리
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Response: {}", response.getBody());
                // 응답 본문을 파싱하여 토큰 응답 생성
                // 가정: 응답 본문은 JSON 형식
                Map<String, Object> responseBody = new ObjectMapper().readValue(response.getBody(), Map.class);

                if (responseBody != null && responseBody.containsKey("access_token") && responseBody.containsKey("refresh_token")) {
                    String accessToken = (String) responseBody.get("access_token");
                    String refreshToken = (String) responseBody.get("refresh_token");
                    return new OAuth2AccessTokenResponse(accessToken, refreshToken);
                } else {
                    throw new IllegalArgumentException("액세스 토큰을 가져오는 중 오류가 발생했습니다.");
                }
            } else {
                log.warn("Request failed with status code: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        return null;
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
                return "GOOGLE_CLIENT_SECRET";
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
