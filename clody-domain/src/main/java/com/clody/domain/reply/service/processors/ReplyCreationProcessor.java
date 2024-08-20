package com.clody.domain.reply.service.processors;

import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.event.ReplyCreatedEvent;
import com.clody.domain.reply.repository.ReplyMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyCreationProcessor {

  private final ReplyMessageSender<Message> replyMessageSender;

  @EventListener
  public void handleReplyCreatedEvent(ReplyCreatedEvent event) {
    log.info("Reply Created Event: {}", event);
    Message message = Message.of(event);
    replyMessageSender.sendMessage(message);
  }
}
