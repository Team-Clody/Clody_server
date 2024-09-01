//package com.clody.clodybatch.service;
//
//import com.clody.domain.alarm.Alarm;
//import com.clody.meta.Schedule;
//import java.time.LocalDateTime;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class ReplyNotificationTasklet implements Tasklet {
//
//  private final ReplyScheduleService replyScheduleService;
//  private final ReplyNotificationService replyNotificationService;
//
//  @Override
//  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
//    List<Schedule> schedules = replyScheduleService.findSchedulesToNotify(LocalDateTime.now());
//    schedules.forEach(schedule -> {
//      Alarm alarm = replyNotificationService.getReplyAlarmForUser(schedule.getUserId());
//      replyNotificationService.sendReplyNotification(alarm);
//      replyScheduleService.updateSchedulesAsSent(schedule);
//    });
//    return RepeatStatus.FINISHED;
//  }
//
//}
