package com.clody.clodybatch.config;

import static com.clody.clodybatch.config.ReplyNotificationStepConfig.STEP_NAME;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReplyJobConfig   {

  public static final String JOB_NAME= "REPLY_NOTIFICATION_JOB";
  private final Step replyNotificationStep;

  public ReplyJobConfig(@Qualifier(STEP_NAME) Step replyNotificationStep) {
    this.replyNotificationStep = replyNotificationStep;
  }

  @Bean(name = JOB_NAME)
  public Job ReplyNotificationJob(JobRepository jobRepository){
    return new JobBuilder(JOB_NAME, jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(replyNotificationStep)
        .build();
  }
}
