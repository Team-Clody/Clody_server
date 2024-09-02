package com.clody.clodybatch;

import static com.clody.clodybatch.config.BatchConfig.BATCH_DATASOURCE;
import static com.clody.clodybatch.config.BatchConfig.BATCH_TRANSACTION_MANAGER;

import com.clody.clodybatch.config.BatchConfig;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;

@Import({BatchConfig.class})
@EnableBatchProcessing(dataSourceRef = BATCH_DATASOURCE, transactionManagerRef = BATCH_TRANSACTION_MANAGER)
@SpringBootApplication
public class ClodyBatchApplication {

  public static void main(String[] args) {
    System.setProperty("spring.config.name","application-batch");
    SpringApplication springApplication = new SpringApplicationBuilder(ClodyBatchApplication.class).web(
        WebApplicationType.NONE).build();
    springApplication.run(args);
  }

}
