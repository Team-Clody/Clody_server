package com.clody.domain.user.event;

import org.springframework.context.ApplicationEventPublisher;

public class UserEventPublisherImpl implements UserEventPublisher{

  private final ApplicationEventPublisher eventPublisher;

  public UserEventPublisherImpl(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  @Override
  public void publishUserCreatedEvent(UserCreatedEvent event) {
    eventPublisher.publishEvent(event);
  }
}