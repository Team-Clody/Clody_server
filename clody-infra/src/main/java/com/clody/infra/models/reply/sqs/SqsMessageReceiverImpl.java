package com.clody.infra.models.reply.sqs;

import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.domain.reply.event.ReplyMessagePublisher;
import com.clody.domain.reply.repository.ReplyMessageReceiver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsMessageReceiverImpl implements ReplyMessageReceiver {

  @Value("${spring.cloud.aws.sqs.queue-name}")
  private  String REPLY_QUEUE;

  private final SqsTemplate sqsTemplate;
  private final ObjectMapper objectMapper;

  private final ReplyMessagePublisher replyMessagePublisher;

  @SqsListener("${spring.cloud.aws.sqs.queue-name}")
  public void receiveMessage(String message) {
    try {
      DequeuedMessage payload = convertMessage(message);
      LocalDateTime messageTime = payload.creationTime();

      if (LocalDateTime.now().isAfter(messageTime.plusSeconds(10))) {
        processMessage(payload);
      } else {
        resendMessage(message);
      }
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public void resendMessage(String message) {
    log.info("Resending message: {}", message);
    sqsTemplate.send(to -> to.queue(REPLY_QUEUE)
        .payload(message));
  }

  @Override
  public void processMessage(DequeuedMessage message) {
    log.info("Processed Message: {}", message.content());
    replyMessagePublisher.publishDequeuedMessage(message);
  }

  private DequeuedMessage convertMessage(String message) throws JsonProcessingException {
    return objectMapper.readValue(message, DequeuedMessage.class);
  }


}
