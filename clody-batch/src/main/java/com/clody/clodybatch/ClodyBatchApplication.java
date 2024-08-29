package com.clody.clodybatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
@Slf4j
public class ClodyBatchApplication {

  public static void main(String[] args) {
    int exit = SpringApplication.exit(SpringApplication.run(ClodyBatchApplication.class, args));
    log.info("exit = {}", exit);
    System.exit(exit);
  }

}
