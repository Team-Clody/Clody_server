package com.clody.infra.models.reply;

import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Component
public class SqsMessageSender {

  private final SqsTemplate sqsTemplate;

  @Value("${cloud.aws.sqs.queue-name")
  private String QUEUE_NAME;

  public SqsMessageSender(SqsAsyncClient sqsAsyncClient) {
    this.sqsTemplate = SqsTemplate.newTemplate(sqsAsyncClient);
  }

  public SendResult<String> sendMessage(String message) {
    return sqsTemplate.send(to -> to
        .queue(QUEUE_NAME)
        .payload(message));
  }
}
