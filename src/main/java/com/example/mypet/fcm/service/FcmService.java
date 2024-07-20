package com.example.mypet.fcm.service;

import com.example.mypet.fcm.dto.FcmSendDto;

import java.io.IOException;

public interface FcmService {

    int sendNotificationToUsersInLocation(FcmSendDto fcmSenDto) throws IOException;
}