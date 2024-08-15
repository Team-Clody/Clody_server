package com.clody.infra.config;

import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class SQSConfig {

  @Value("${spring.cloud.aws.credentials.access-key}")
  private  String AWS_ACCESS_KEY;

  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String AWS_SECRET_KEY;

  @Value("${spring.cloud.aws.region.static}")
  private String AWS_REGION;

  @Bean
  public SqsAsyncClient sqsAsyncClient(){
    return SqsAsyncClient.builder()
        .credentialsProvider(() -> new AwsCredentials() {
          @Override
          public String accessKeyId() {
            return AWS_ACCESS_KEY;
          }

          @Override
          public String secretAccessKey() {
            return AWS_SECRET_KEY;
          }
        })
        .region(Region.of(AWS_REGION))
        .build();
  }

  @Bean
  //Listener
  public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient){
    return SqsMessageListenerContainerFactory.builder()
        .sqsAsyncClient(sqsAsyncClient)
        .build();
  }

  @Bean
  public SqsTemplate sqsTemplate(){
    return SqsTemplate.newTemplate(sqsAsyncClient());
  }
}
