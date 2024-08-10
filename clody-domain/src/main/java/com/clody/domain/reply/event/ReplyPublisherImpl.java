package com.clody.domain.reply.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyPublisherImpl implements ReplyPublisher {

  private final ApplicationEventPublisher eventPublisher;

  @Override
  public void publish(ReplyCreatedEvent event) {
    eventPublisher.publishEvent(event);
  }
}
