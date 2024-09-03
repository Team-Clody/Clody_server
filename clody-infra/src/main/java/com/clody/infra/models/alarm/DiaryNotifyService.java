package com.clody.infra.models.alarm;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.infra.external.fcm.FcmService;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryNotifyService {

  private final AlarmRepository alarmRepository;
  private final FcmService fcmService;

  @Scheduled(cron = "0 0/10 * * * *")
  public void sendDiaryAlarms() {
    LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.SECONDS);

    List<Alarm> alarmTarget = alarmRepository.findAllByTime(time);

    alarmTarget.stream()
        .filter(Alarm::isDiaryAlarm)
        .forEach(
            it -> fcmService.sendDiaryAlarm(it.getFcmToken())
        );
    log.info("Alarm Successfully Sent");
  }

}
