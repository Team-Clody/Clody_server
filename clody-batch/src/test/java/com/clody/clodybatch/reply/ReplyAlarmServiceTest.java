package com.clody.clodybatch.reply;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clody.clodybatch.service.ReplyNotificationService;
import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.repository.AlarmRepository;
import com.clody.domain.alarm.service.NotificationSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReplyAlarmServiceTest {

  @Mock
  private AlarmRepository alarmRepository;

  @Mock
  private NotificationSender notificationSender;

  @Mock
  private ReplyNotificationService replyNotificationService;

  @Test
  public void 알람_발송_테스트(){
    Alarm alarm = new Alarm();
    alarm.renewFcmToken("testToken");
    Long userId = 1L;

    replyNotificationService.sendReplyNotification(alarm);
    verify(replyNotificationService).sendReplyNotification(alarm);
  }

  @Test
  public void 알람_검색_테스트(){
    Alarm alarm = new Alarm();
    alarm.renewFcmToken("testToken");
    Long userId = 1L;

    when(alarmRepository.findByUserId(1L)).thenReturn(alarm);
    Alarm found = alarmRepository.findByUserId(userId);

    assert(found.getFcmToken()).equals("testToken");
  }

}
