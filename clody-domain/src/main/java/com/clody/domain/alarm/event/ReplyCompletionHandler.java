package com.clody.domain.alarm.event;

import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.meta.repository.ScheduleMetaRepository;
import com.clody.domain.alarm.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyCompletionHandler {

  private final NotificationSender sender;
  private final AlarmRepository alarmRepository;
  private final ScheduleMetaRepository scheduleMetaRepository;

  @EventListener
  public void handleReplyCompletion(ScheduleEvent event) {
    scheduleMetaRepository.save(event.schedule());
    log.info("알림 스케줄 정상 등록: {}", event.schedule());
  }
}
