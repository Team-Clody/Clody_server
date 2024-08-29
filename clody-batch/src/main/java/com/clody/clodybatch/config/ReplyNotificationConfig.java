package com.clody.clodybatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class ReplyNotificationConfig {

  @Bean
  public Job replyNotificationJob(JobRepository jobRepository, Step step) {
    return new JobBuilder("replyNotificationJob", jobRepository)
        .start(step)
        .build();
  }

}
