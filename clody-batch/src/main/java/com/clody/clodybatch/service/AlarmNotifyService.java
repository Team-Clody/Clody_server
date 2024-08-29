package com.clody.clodybatch.service;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.alarm.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmNotifyService {

  private final AlarmRepository alarmRepository;
  private final NotificationSender notificationSender;

  public Alarm getReplyAlarmForUser(Long userId){
    return alarmRepository.findByUserId(userId);
  }

  public void sendReplyNotification(Alarm alarm){
    notificationSender.sendReplyAlarm(alarm.getFcmToken());
  }
}
