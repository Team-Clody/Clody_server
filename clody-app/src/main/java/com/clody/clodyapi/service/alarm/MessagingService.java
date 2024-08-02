package com.clody.clodyapi.service.alarm;

public interface MessagingService {

  void sendDiaryAlarm(String token);

  void sendReplyAlarm(String fcmToken);
}
