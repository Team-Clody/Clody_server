package com.clody.infra.models.reply.sqs;

import static com.clody.support.constants.MessageProcessConstants.DEFAULT_DURATION;
import static com.clody.support.constants.MessageProcessConstants.FIRST_DURATION;
import static com.clody.support.constants.MessageProcessConstants.STATIC_DURATION;

import com.clody.domain.reply.ReplyType;
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
      ReplyType replyType = payload.type();

      long delayInSeconds;
      if (replyType == ReplyType.FIRST) {
        delayInSeconds = FIRST_DURATION; // 1분
      } else if (replyType == ReplyType.DYNAMIC) {
        delayInSeconds = DEFAULT_DURATION; // 5분
      } else {
        delayInSeconds = STATIC_DURATION;
      }

      if (LocalDateTime.now().isAfter(messageTime.plusSeconds(delayInSeconds))) {
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
