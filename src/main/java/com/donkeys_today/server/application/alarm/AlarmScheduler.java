package com.donkeys_today.server.application.alarm;

import com.donkeys_today.server.domain.alarm.Alarm;
import com.donkeys_today.server.external.fcm.FcmService;
import com.donkeys_today.server.external.fcm.MessageContent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmScheduler {

  private final FcmService fcmService;
  private final AlarmService alarmService;

  @Scheduled(cron = "0 */1 * * * *")//Every 10 minutes
  public void checkAndSendAlarms(){

    LocalTime currentTime = LocalTime.now();
    List<Alarm> alarmList = alarmService.findAlarmsByCurrentTime(currentTime);

    for(Alarm userAlarm : alarmList){
      if(validateAlarm(userAlarm)){
        fcmService.sendDiaryAlarm(userAlarm.getFcmToken(), MessageContent.DIARY_WRITE_REQUEST);
      }
    }
  }

  private boolean validateAlarm(Alarm userAlarm) {
    return userAlarm.isDiaryAlarm() && userAlarm.getFcmToken()!=null && !userAlarm.getFcmToken().isEmpty();
  }

}
