package com.clody.domain.alarm.service;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.dto.AlarmTotalInfo;
import com.clody.domain.alarm.dto.UpdateAlarmCommand;
import com.clody.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmCommandService {

  private final AlarmRepository alarmRepository;

  public AlarmTotalInfo updateAlarm(Long userId, UpdateAlarmCommand command){
    Alarm alarm = alarmRepository.findByUserId(userId);
    alarm.updateAlarm(command);
    alarmRepository.save(alarm);
    return AlarmTotalInfo.of(alarm);
  }

  @Transactional
  public void updateToken(Long userId, String fcmToken){
    Alarm alarm = alarmRepository.findByUserId(userId);
    alarm.renewFcmToken(fcmToken);
  }
}
