package com.clody.clodybatch.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@JobScope
@NoArgsConstructor
@Getter
@Slf4j
public class ReplyNotificationJobParameter {

  private LocalDateTime dateTime;

  @Value("${chunk-size:1000}")
  private int chunkSize;

  @Value("#{jobParameters[dateTime]}")
  public void setDateTime(String dateTime){
    log.info("Job Parameter dateTime: {}", dateTime);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    this.dateTime = LocalDateTime.parse(dateTime, formatter);
  }

}
