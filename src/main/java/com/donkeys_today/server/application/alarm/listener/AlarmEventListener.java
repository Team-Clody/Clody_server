package com.donkeys_today.server.application.alarm.listener;

import com.donkeys_today.server.application.user.event.UserSignUpEvent;
import com.donkeys_today.server.domain.alarm.Alarm;
import com.donkeys_today.server.domain.alarm.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {

  private final AlarmRepository alarmRepository;

  @EventListener
  private void onUserRegisteredAlarmEvent(UserSignUpEvent event) {
      Alarm defaultAlarm = Alarm.createInitailDiaryAlarm(event.getUser());
      alarmRepository.save(defaultAlarm);
  }

}
