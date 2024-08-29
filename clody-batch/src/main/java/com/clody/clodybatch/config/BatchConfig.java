package com.clody.clodybatch.config;

import com.clody.infra.meta.JpaScheduleMetaRepository;
import com.clody.meta.Schedule;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig extends DefaultBatchConfiguration {

  @Bean
  public RepositoryItemReader<Schedule> metaScheduleReader(
      JpaScheduleMetaRepository scheduleMetaRepository){
    RepositoryItemReader<Schedule> reader = new RepositoryItemReader<>();
    reader.setRepository(scheduleMetaRepository);
    reader.setMethodName("findByNotificationTime");
    reader.setArguments(Collections.singletonList(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)));
    reader.setPageSize(10);
    return reader;
  }
}
