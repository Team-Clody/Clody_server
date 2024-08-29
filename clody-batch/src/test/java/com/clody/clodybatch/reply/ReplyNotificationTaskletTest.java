package com.clody.clodybatch.reply;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.clody.clodybatch.service.AlarmNotifyService;
import com.clody.clodybatch.service.ReplyNotificationTasklet;
import com.clody.clodybatch.service.ScheduleService;
import com.clody.domain.alarm.Alarm;
import com.clody.domain.reply.ReplyType;
import com.clody.domain.user.Platform;
import com.clody.domain.user.User;
import com.clody.meta.Schedule;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;

@ExtendWith(MockitoExtension.class)
public class ReplyNotificationTaskletTest {

  @Mock
  private ScheduleService scheduleService;

  @Mock
  private AlarmNotifyService alarmNotifyService;

  @InjectMocks
  private ReplyNotificationTasklet replyNotificationTasklet;

  @Test
  public void 배치_답변_완료_알림_테스트() throws Exception{
    LocalDateTime now = LocalDateTime.now();
    Long userId = 1L;
    LocalDateTime notificationTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    Schedule schedule = Schedule.create(userId,notificationTime, ReplyType.DYNAMIC);
    User user = User.createNewUser("testId", Platform.KAKAO, "test@gmail.com", "testUser");
    Alarm alarm = Alarm.createInitailDiaryAlarm(user);

    when(scheduleService.findSchedulesToNotify(any(LocalDateTime.class))).thenReturn(Collections.singletonList(schedule));
    when(alarmNotifyService.getReplyAlarmForUser(1L)).thenReturn(alarm);

    replyNotificationTasklet.execute(Mockito.mock(StepContribution.class),Mockito.mock(ChunkContext.class));

    verify(alarmNotifyService).sendReplyNotification(alarm);
    verify(scheduleService).updateScheduleAsSent(schedule);
  }

}
