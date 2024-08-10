package com.clody.domain.reply.event;

public interface ReplyPublisher {

  void publish(ReplyCreatedEvent event);
}
