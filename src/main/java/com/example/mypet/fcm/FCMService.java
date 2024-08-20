package com.example.mypet.fcm;


import com.example.mypet.global.ex.ResourceNotFoundException;
import com.example.mypet.petSearchBoard.PetSearchBoard;
import com.example.mypet.petSearchBoard.PetSearchBoardRepository;
import com.example.mypet.security.repository.UsersRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Log4j2
public class FCMService {

    private final UsersRepository usersRepository;
    private final PetSearchBoardRepository petSearchBoardRepository;

    public void updateFcmToken(String userId, String fcmToken) {
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        user.setFcmToken(fcmToken);
        usersRepository.save(user);
    }

    public void sendMessagesToAll(String boardId) throws IOException, FirebaseMessagingException {
        var usersList = usersRepository.findAll();
        var petSearchBoard = petSearchBoardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("게시판을 찾을 수 없습니다")
        );
        for (var user : usersList) {

            String fcmToken = user.getFcmToken();
            if (fcmToken != null && !fcmToken.isEmpty()) {
                Message message;
                if (petSearchBoard.getImageUrlList().size() > 0) {
                    message = Message.builder()
                        .setToken(fcmToken)
                        .setNotification(
                            Notification.builder()
                                .setTitle(petSearchBoard.getSpecificLocation())
                                .setBody("근처에 도움이 필요한 유기견이 있습니다. 한 생명을 구해주세요!")
                                .setImage(petSearchBoard.getImageUrlList().get(0))
                                .build()
                        ).build();
                } else {
                    message = Message.builder()
                        .setToken(fcmToken)
                        .setNotification(
                            Notification.builder()
                                .setTitle(petSearchBoard.getSpecificLocation())
                                .setBody("근처에 도움이 필요한 유기견이 있습니다. 한 생명을 구해주세요!")
                                .build()
                        ).build();
                }

                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println(response);
                log.info("Successfully sent message to user: {}, response: {}", user.getId(), response);
            }
        }

    }
}