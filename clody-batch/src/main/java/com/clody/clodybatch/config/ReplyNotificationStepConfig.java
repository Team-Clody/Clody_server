package com.clody.clodybatch.config;

import com.clody.clodybatch.service.ReplyNotificationService;
import com.clody.clodybatch.service.ReplyScheduleService;
import com.clody.meta.Schedule;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
//@EnableBatchProcessing
public class ReplyNotificationStepConfig {

  public static final String STEP_NAME = ReplyJobConfig.JOB_NAME + ".STEP";

  private final ReplyNotificationService replyNotificationService;
  private final ReplyScheduleService replyScheduleService;
  private final EntityManagerFactory entityManagerFactory;
  private final ReplyNotificationJobParameter jobParameter;
//  private final ScheduleMetaRepository scheduleMetaRepository;

  @Bean(STEP_NAME)
  @JobScope
  public Step replyNotificationStep(
      JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    log.info(">>>>> This is Step1");

    return new StepBuilder(STEP_NAME, jobRepository)
        .<Schedule, Schedule>chunk(jobParameter.getChunkSize(), platformTransactionManager)
        .reader(scheduleItemReader())
        .writer(changeScheduleStatus())
        .build();
  }

  @Bean
  @StepScope
  public ItemReader<Schedule> scheduleItemReader() {
    return new JpaPagingItemReaderBuilder<Schedule>()
        .name("ScheduleItemReader")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(jobParameter.getChunkSize())
        .queryString(
            "SELECT s FROM Schedule s WHERE s.isSent = false AND s.notificationTime <= :now")
        .build();
  }

  @Bean
  public ItemWriter<Schedule> changeScheduleStatus(){
    return schedules ->{
      for (Schedule schedule : schedules){
        schedule.notifySent();
//        replyScheduleService.updateSchedulesAsSent(schedule);
      }
    };
  }

}
