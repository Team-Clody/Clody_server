package com.clody.infra.models.reply.sqs;

import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.repository.ReplyMessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SqsMessageSenderImpl implements ReplyMessageSender<Message>{

  @Value("${spring.cloud.aws.sqs.queue-name}")
  private String REPLY_QUEUE;

  private final SqsTemplate sqsTemplate;
  private final ObjectMapper objectMapper;

  public void sendMessage(Message message)   {
    try {
      String parsedMessage = objectMapper.writeValueAsString(message);
      log.info("Sending message: {}", parsedMessage);
      log.info(REPLY_QUEUE);
      sqsTemplate.send(to -> to.queue(REPLY_QUEUE)
          .payload(parsedMessage));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  private Message convertMessage(String message) throws JsonProcessingException {
    return objectMapper.readValue(message, Message.class);
  }
}
