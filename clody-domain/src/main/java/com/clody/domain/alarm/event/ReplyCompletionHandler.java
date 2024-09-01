package com.clody.domain.alarm.event;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.dto.ScheduleAlarmInfo;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.alarm.service.AlarmScheduler;
import com.clody.domain.alarm.strategy.ScheduleAlarmTimeFactory;
import com.clody.domain.alarm.strategy.ScheduleTimeStrategy;
import com.clody.domain.reply.dto.CreationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyCompletionHandler {

  private final AlarmRepository alarmRepository;
  private final ScheduleAlarmTimeFactory alarmTimeFactory;
  private final AlarmScheduler alarmScheduler;

  @EventListener
  public void handleReplyCompletion(CreationMessage event) {
    /*
    * 사용자가 알람 수신에 동의한 경우, 알림 스케줄에 등록
     */
    Alarm alarm = alarmRepository.findByUserId(event.userId());
    alarm.validateUserAgreedForReplyAlarm();

    ScheduleTimeStrategy scheduleTimeStrategy = alarmTimeFactory.getStrategy(event.type());
    scheduleTimeStrategy.calculateAlarmDelayTime()
        .map(rt -> ScheduleAlarmInfo.of(event, rt, alarm.getFcmToken()))
        .ifPresent(info -> {
          alarmScheduler.scheduleReplyPushAlarm(info);
          log.info("알림 스케줄 정상 등록: {}", info);
        });
  }
}
