package com.donkeys_today.server.external.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmService {

  private final FirebaseMessaging firebaseMessaging;

  public void sendDiaryAlarm(String fcmToken, MessageContent messageContent){
    Notification notification = Notification.builder()
        .setTitle(messageContent.getTitle())
        .setBody(messageContent.getBody())
        .build();

    Message msg = Message.builder()
        .setToken(fcmToken)
        .setNotification(notification)
        .build();
    try {
      log.info("token: " + fcmToken);
      String response = firebaseMessaging.send(msg);
      log.info("Successfully Sent Message : "+ response);
    }catch (Exception e){
      e.printStackTrace();
    }
    //TODO 푸시알림 전송 실패 시 redis에서 제거해야함.

  }

}
