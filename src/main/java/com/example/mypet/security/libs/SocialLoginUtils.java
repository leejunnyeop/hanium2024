package com.example.mypet.security.libs;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SocialLoginUtils {
    private static final String google_apiUrl = "https://oauth2.googleapis.com/tokeninfo";
    private final RestTemplate restTemplate;

    public JSONObject getGoogleInfo(String idToken) throws IOException {

        log.info("getGoogleInfo 시작해");

        var uriBuilder = UriComponentsBuilder.fromHttpUrl(google_apiUrl)
                .queryParam("id_token", idToken);
        log.info("uriBuilder 조사할거야 : " + uriBuilder);
        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);
        log.info("getGoogleInfo 에서 response 조사 할거야 : " + response);

        JSONObject json = null;

        if (response.getStatusCode() == HttpStatus.OK) {
            json = new JSONObject(response.getBody());
        }
        else {
            throw new IOException(response.getBody());
        }
        log.info("getGoogleInfo 에서 return json 조사할거야 : " + json);
        return json;

    }

}
