package com.clody.infra.models.alarm;

import com.clody.domain.alarm.dto.ScheduleAlarmInfo;
import com.clody.domain.alarm.service.AlarmScheduler;
import com.clody.domain.alarm.strategy.RelativeTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmQuartzScheduler implements AlarmScheduler {

  private final static String JOB_DETAIL_PREFIX ="replyNotification-";
  private final static String TRIGGER_PREFIX ="replyNotificationTrigger-";

  private final Scheduler quartzScheduler;

  @Override
  public void scheduleReplyPushAlarm(ScheduleAlarmInfo info) {
    JobDetail jobDetail = JobBuilder.newJob(ReplayNotificationJob.class)
        .withIdentity(JOB_DETAIL_PREFIX+info.replyId())
        .usingJobData("replyId",info.replyId())
        .usingJobData("userId",info.userId())
        .usingJobData("fcmToken", info.fcmToken())
        .build();

    RelativeTime delay = info.delay();

    Trigger trigger = TriggerBuilder.newTrigger()
        .withIdentity(TRIGGER_PREFIX+info.replyId())
        .startAt(new Date(System.currentTimeMillis()+ delay.timeUnit().toMillis(delay.amount())))
        .build();

    try {
      quartzScheduler.scheduleJob(jobDetail,trigger);
    } catch (SchedulerException e) {
      throw new RuntimeException(e);
    }
  }

}
