package com.clody.domain.reply.service.processors;

import com.clody.domain.alarm.event.AlarmPublisher;
import com.clody.domain.alarm.event.CompletionEvent;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.dto.ReplyInsertionInfo;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.reply.service.ReplyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyCompletionProcessor {

  private final ReplyService replyService;
  private final AlarmPublisher alarmPublisher;
  private final ReplyRepository replyRepository;
  private final AlarmEventConverter alarmEventConverter;

  @EventListener
  @Transactional
  public void insertReply(Message message){
    ReplyInsertionInfo info = ReplyInsertionInfo.of(message.replyId(), message.content());
    insertReply(info);

    log.info("Reply Inserted: {}", info.content());
    CompletionEvent completionEvent = alarmEventConverter.convertToCompletionEvent(info.replyId());
    alarmPublisher.publishCompletionEvent(completionEvent);
  }

  public void insertReply(ReplyInsertionInfo info) {
    Reply reply = replyRepository.findById(info.replyId());
    reply.insertContentFromRody(info.content());
    replyRepository.save(reply);
  }
}
