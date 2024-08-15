package com.clody.clodyapi.reply.service;

import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.infra.models.reply.RodyProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RodyMessageProcessor {

  private final RodyProcessor rodyProcessor;

  @EventListener
  public void sendEnqueuedMessageToRody(DequeuedMessage message){
    rodyProcessor.processMessage(message);
  }

}
