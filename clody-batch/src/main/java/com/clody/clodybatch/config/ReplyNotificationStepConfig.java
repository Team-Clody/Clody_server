package com.clody.clodybatch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class ReplyNotificationStepConfig {

  @Bean
  public Tasklet replyNotificationTasklet() {
    return (contribution, chunkContext) -> {
      System.out.println("Sending notification to user: " + chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("fcmToken"));
      return null;
    };
  }

  @Bean
  public Step myStep(JobRepository jobRepository, Tasklet replyNotificationTasklet, PlatformTransactionManager transactionManager) {
    return new StepBuilder("myStep", jobRepository)
        .tasklet(replyNotificationTasklet,transactionManager)
        .build();
  }
}
