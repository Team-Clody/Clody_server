package com.clody.clodyapi.service.alarm;

import com.clody.domain.alarm.Alarm;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmScheduler {

  private final MessagingService messagingService;
  private final AlarmService alarmService;

  @Scheduled(cron = "0 */1 * * * *")//매 1분마다 실행
  @Async
  public void checkAndSendAlarms() {

    LocalTime currentTime = LocalTime.now().withSecond(0).withNano(0);
    List<Alarm> alarmList = alarmService.findAlarmsByCurrentTime(currentTime);

    log.info("Executing scheduled task at {}", currentTime);
    log.info("alarmList Size : {}", alarmList.size());

    for (Alarm userAlarm : alarmList) {
      if (validateAlarm(userAlarm)) {
        messagingService.sendDiaryAlarm(userAlarm.getFcmToken());
      }
    }
  }

  private boolean validateAlarm(Alarm userAlarm) {
    return userAlarm.isDiaryAlarm() && userAlarm.getFcmToken() != null && !userAlarm.getFcmToken()
        .isEmpty();
  }

}
