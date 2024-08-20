package com.clody.domain.reply.service.processors;

import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.domain.reply.service.RodyProcessor;
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
  public void sendDequeuedMessageToRody(DequeuedMessage message){
    rodyProcessor.createReply(message);
  }

}
