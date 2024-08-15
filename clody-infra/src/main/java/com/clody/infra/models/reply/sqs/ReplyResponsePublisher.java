package com.clody.infra.models.reply.sqs;

import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.event.ReplyMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyResponsePublisher implements ReplyMessagePublisher {

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publishMessage(Message message){
    applicationEventPublisher.publishEvent(message);
  }

  @Override
  public void publishDequeuedMessage(DequeuedMessage message){
    applicationEventPublisher.publishEvent(message);
  }
}
