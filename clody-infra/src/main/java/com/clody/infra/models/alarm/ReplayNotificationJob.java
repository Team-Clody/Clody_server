package com.clody.infra.models.alarm;

import com.clody.domain.alarm.service.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplayNotificationJob implements Job {

  public static final String FCM_TOKEN = "fcmToken";
  private final NotificationSender notificationSender;

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
    String userFcmToken = jobDataMap.getString(FCM_TOKEN);

    notificationSender.sendReplyAlarm(userFcmToken);
  }
}
