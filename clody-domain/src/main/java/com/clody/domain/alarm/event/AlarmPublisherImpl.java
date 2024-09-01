package com.clody.domain.alarm.event;

import com.clody.domain.reply.dto.CreationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmPublisherImpl implements AlarmPublisher{

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publishCompletionEvent(CreationMessage creationMessage) {
    applicationEventPublisher.publishEvent(creationMessage);
  }
}
