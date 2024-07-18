package com.example.mypet.security.libs;

import lombok.RequiredArgsConstructor;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class SocialLoginUtils {
    private static final String google_apiUrl = "https://oauth2.googleapis.com/tokeninfo";
    private final RestTemplate restTemplate;

    public JSONObject getGoogleInfo(String idToken) throws IOException {


        var uriBuilder = UriComponentsBuilder.fromHttpUrl(google_apiUrl)
                .queryParam("id_token", idToken);

        ResponseEntity<String> response = restTemplate.getForEntity(uriBuilder.toUriString(), String.class);


        JSONObject json = null;

        if (response.getStatusCode() == HttpStatus.OK) {
            json = new JSONObject(response.getBody());
        }
        else {
            throw new IOException(response.getBody());
        }

        return json;

    }

}
