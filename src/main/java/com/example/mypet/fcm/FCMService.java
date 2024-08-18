package com.example.mypet.fcm;


import com.example.mypet.fcm.domain.FCMMessageDto;
import com.example.mypet.security.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FCMService {

    private static final String API_URL = "https://fcm.googleapis.com/v1/projects/puppy-life-dc6ab/messages:send";
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final UsersRepository usersRepository;

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/puppy-life-dc6ab-firebase-adminsdk-2lrq2-8b68bc6b56.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

    private String makeMessage(String token, String boardId) throws JsonProcessingException {
        // 고정된 제목과 본문
        String title = "해피 마루";
        String body = "근처에 도움이 필요한 유기견이 있습니다. 한 생명을 구해주세요!";

        FCMMessageDto.Data data = FCMMessageDto.Data.builder()
                .name("boardId")
                .description(boardId)
                .build();

        FCMMessageDto fcmMessage = FCMMessageDto.builder()
                .message(FCMMessageDto.Message.builder()
                        .token(token)  // 특정 사용자에게 메시지 전송
                        .notification(FCMMessageDto.Notification.builder()
                                .title(title)  // 고정된 제목
                                .body(body)    // 고정된 본문
                                .build()
                        )
                        .data(data)
                        .build())
                .validateOnly(false)
                .build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    public void updateFcmToken(String userId, String fcmToken) {
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        user.setFcmToken(fcmToken);
        usersRepository.save(user);
    }
    public void sendMessagesToAll(String boardId) throws IOException {

        String accessToken = getAccessToken();
        var usersList = usersRepository.findAll();
        var fcmTokenList = new ArrayList<String>();
        for (var user : usersList){

            if (user.getFcmToken() != null&& !user.getFcmToken().isEmpty()){
                fcmTokenList.add(user.getFcmToken());
            }
        }
        for (String token : fcmTokenList) {
            String message = makeMessage(token, boardId);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(message, headers);

            try {
                ResponseEntity<String> response = restTemplate.exchange(
                        API_URL,
                        HttpMethod.POST,
                        entity,
                        String.class
                );

                if (!response.getStatusCode().is2xxSuccessful()) {
                    throw new RestClientException("푸시 알림 전송 실패 (토큰: " + token + ")");
                }
            } catch (RestClientException e) {
                throw new IOException("푸시 알림 전송 중 오류 발생 (토큰: " + token + ")", e);
            }
        }
    }
}