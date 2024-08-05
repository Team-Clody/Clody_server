package com.clody.domain.alarm.service;

public interface MessageSender {

  void sendDiaryAlarm(String token);

  void sendReplyAlarm(String fcmToken);
}
