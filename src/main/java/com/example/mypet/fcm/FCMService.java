package com.example.mypet.fcm;


import com.example.mypet.alarm.AlarmRepository;
import com.example.mypet.alarm.AlarmRequestDto;
import com.example.mypet.alarm.AlarmService;
import com.example.mypet.alarm.AlarmedType;
import com.example.mypet.global.ex.ResourceNotFoundException;
import com.example.mypet.petSearchBoard.PetSearchBoardRepository;
import com.example.mypet.security.repository.UsersRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Log4j2
public class FCMService {

    private final UsersRepository usersRepository;
    private final PetSearchBoardRepository petSearchBoardRepository;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    public void updateFcmToken(String userId, String fcmToken) {
        var user = usersRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        user.setFcmToken(fcmToken);
        usersRepository.save(user);
    }

    @Async
    public void sendMessagesToAll(String boardId) throws IOException, FirebaseMessagingException {
        var usersList = usersRepository.findAll();
        var petSearchBoard = petSearchBoardRepository.findById(boardId).orElseThrow(
                () -> new ResourceNotFoundException("게시판을 찾을 수 없습니다")
        );

        for (var user : usersList) {

            String fcmToken = user.getFcmToken();
            if (fcmToken != null && !fcmToken.isEmpty()) {

                Message message;
                if (!petSearchBoard.getImageUrlList().isEmpty()) {
                    message = Message.builder()
                            .setToken(fcmToken)
                            .setNotification(
                                    Notification.builder()
                                            .setTitle("근처 유실견 알림")
                                            .setBody("근처에 도움이 필요한 강아지가 있어요. 한 생명을 인근 보호소로 안전하게 인도해보세요")
                                            .setImage(petSearchBoard.getImageUrlList().get(0))
                                            .build()
                            ).build();
                } else {
                    message = Message.builder()
                            .setToken(fcmToken)
                            .setNotification(
                                    Notification.builder()
                                            .setTitle("근처 유실견 알림")
                                            .setBody("근처에 도움이 필요한 강아지가 있어요. 한 생명을 인근 보호소로 안전하게 인도해보세요")
                                            .build()
                            ).build();
                }
                try {
                    String response = FirebaseMessaging.getInstance().send(message);
                    System.out.println(response);
                    log.info("Successfully sent message to user: {}, response: {}", user.getId(), response);
                    // alarm 저장 -> 비동기처리 나중에 생각
                    var alarm = alarmService
                            .createAlarm(user.getId(), new AlarmRequestDto(petSearchBoard.getSpecificLocation(), AlarmedType.STRAY_DOG_NEARBY, petSearchBoard.getId()));
                    alarmRepository.save(alarm);

                } catch (Exception e) {
                    log.error("Failed to send message to user: {}, error: {}", user.getId(), e.getMessage());
                }

            }
        }

    }
}