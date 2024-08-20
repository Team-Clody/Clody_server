package com.clody.infra.external.fcm;

import com.clody.domain.alarm.service.NotificationSender;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FcmService implements NotificationSender {

  private final FirebaseMessaging firebaseMessaging;

  public void sendDiaryAlarm(String fcmToken){
    MessageContent diaryAlert = MessageContent.REPLY_COMPLETED_REQUEST;
    Notification notification = Notification.builder()
        .setTitle(diaryAlert.getTitle())
        .setBody(diaryAlert.getBody())
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

  public void sendReplyAlarm(String fcmToken){
    MessageContent replyAlert = MessageContent.REPLY_COMPLETED_REQUEST;
    Notification notification = Notification.builder()
        .setTitle(replyAlert.getTitle())
        .setBody(replyAlert.getBody())
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
  }

}
