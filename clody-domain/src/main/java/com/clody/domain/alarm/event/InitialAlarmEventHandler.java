package com.clody.domain.alarm.event;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.user.User;
import com.clody.domain.user.event.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitialAlarmEventHandler {

  private final AlarmRepository alarmRepository;

  @EventListener
  public void handleUserCreatedEvent(UserCreatedEvent event){
    //빈 알람 객체 생성 및 저장
    User user = event.getUser();
    Alarm alarm = Alarm.createInitailDiaryAlarm(user);
    alarmRepository.save(alarm);
  }
}
