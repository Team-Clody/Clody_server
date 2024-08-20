package com.clody.domain.alarm.event;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.alarm.service.NotificationSender;
import com.clody.domain.user.User;
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

  @EventListener
  public void handleReplyCompletion(CompletionEvent event) {
    User user = event.user();
    Alarm alarm = alarmRepository.findByUser(user);
    alarm.validateUserAgreedForReplyAlarm();
//    sender.sendReplyAlarm(alarm.getFcmToken());
    log.info("알림 발송 완료: {}", event.replyId());
  }

}
