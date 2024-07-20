package com.example.mypet.fcm.service;


import com.example.mypet.fcm.dto.FcmMessageDto;
import com.example.mypet.fcm.dto.FcmSendDto;

import com.example.mypet.security.repository.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    private final UsersRepository usersRepository;


    @Override
    public int sendNotificationToUsersInLocation(FcmSendDto fcmSenDto) throws IOException {

/***
 *
 * List<Users> usersInLocation = usersRepository.findByLocation(fcmSenDto.getLocation());
        int successCount = 0;
        for (Users user : usersInLocation) {
            try {
                int result = sendMessageTo(fcmSenDto.getToken(), fcmSenDto.getTitle(), fcmSenDto.getBody(), fcmSenDto.getImageUrl());
                if (result == 1) {
                    successCount++;
                }
            } catch (IOException e) {
                throw new ServiceException("알람 보내는데 실패 했습니다");

            }
        }
        return successCount;
    }
***/
return sendMessageTo(fcmSenDto.getToken(), fcmSenDto.getTitle(), fcmSenDto.getBody(), fcmSenDto.getImageUrl());
    }


    public int sendMessageTo(String token, String title, String body, String imageUrl) throws IOException {
        String message = makeMessage(token, title, body, imageUrl);
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(message, headers);

        String API_URL = "https://fcm.googleapis.com/v1/projects/puppy-life-dc6ab/messages:send";
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);


        return response.getStatusCode() == HttpStatus.OK ? 1 : 0;
    }


    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "puppy-life-dc6ab-firebase-adminsdk-2lrq2-1eade08d01.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }


    private String makeMessage(String token, String title, String body, String imageUrl) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        FcmMessageDto fcmMessageDto = FcmMessageDto.builder()
                .message(FcmMessageDto.Message.builder()
                        .token(token)
                        .notification(FcmMessageDto.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(imageUrl)
                                .build()
                        ).build()).vailDateOnly(false).build();

        return om.writeValueAsString(fcmMessageDto);
    }



}