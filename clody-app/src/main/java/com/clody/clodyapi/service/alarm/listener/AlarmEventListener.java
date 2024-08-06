//package com.clody.clodyapi.service.alarm.listener;
//
//import com.clody.clodyapi.service.user.event.UserSignUpEvent;
//import com.clody.domain.alarm.Alarm;
//import com.clody.domain.alarm.repository.AlarmRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class AlarmEventListener {
//
//  private final AlarmRepository alarmRepository;
//
//  @EventListener
//  private void onUserRegisteredAlarmEvent(UserSignUpEvent event) {
//      Alarm defaultAlarm = Alarm.createInitailDiaryAlarm(event.getUser());
//      alarmRepository.save(defaultAlarm);
//  }
//
//}
