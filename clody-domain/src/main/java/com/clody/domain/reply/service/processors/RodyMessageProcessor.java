package com.clody.domain.reply.service.processors;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.DequeuedMessage;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.reply.service.RodyProcessor;
import com.clody.support.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RodyMessageProcessor {

  private final RodyProcessor rodyProcessor;
  private final ReplyRepository replyRepository;

  @EventListener
  public void sendDequeuedMessageToRody(DequeuedMessage message){
    try {
      Reply reply = replyRepository.findById(message.replyId());

      if (reply.checkReplyDeleted()) {
        log.info("Deleted reply: {}", reply.getId());
        return;
      }

      rodyProcessor.createReply(message);
    } catch (NotFoundException e) {
      log.info("Reply 발견 실패 : {}. 프로세스 스킵.", message.replyId());
    } catch (Exception e) {
      log.error("처리 중 오류가 발생했습니다. Reply Id: {}. Error: {}", message.replyId(), e.getMessage());
    }
  }

}
