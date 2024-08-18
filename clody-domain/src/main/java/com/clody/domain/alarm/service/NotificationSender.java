package com.clody.domain.alarm.service;

public interface NotificationSender {

  void sendDiaryAlarm(String token);

  void sendReplyAlarm(String fcmToken);
}
