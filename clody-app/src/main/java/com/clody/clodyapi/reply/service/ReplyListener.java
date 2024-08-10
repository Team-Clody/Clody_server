package com.clody.clodyapi.reply.service;

import com.clody.domain.reply.event.ReplyCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyListener {

  //SQS 등 외부 이벤트 필요

  @EventListener
  public void handleReply(ReplyCreatedEvent event) {
    log.info("Reply Created Event: {}", event);
  }

}
