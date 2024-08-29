package com.clody.clodybatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

@EnableBatchProcessing
@SpringBootApplication
@Slf4j
@ConditionalOnMissingBean(value = DefaultBatchConfiguration.class, annotation = EnableBatchProcessing.class)
public class ClodyBatchApplication {

  public static void main(String[] args) {
    int exit = SpringApplication.exit(SpringApplication.run(ClodyBatchApplication.class, args));
    log.info("exit = {}", exit);
    System.exit(exit);
  }

}
