package com.clody.domain.reply.service.processors;

import com.clody.domain.alarm.event.AlarmPublisher;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.CreationMessage;
import com.clody.domain.reply.dto.Message;
import com.clody.domain.reply.dto.ReplyInsertionInfo;
import com.clody.domain.reply.repository.ReplyRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyCompletionProcessor {

  private final AlarmPublisher alarmPublisher;
  private final ReplyRepository replyRepository;
  private final AlarmEventConverter alarmEventConverter;

  @EventListener
  @Transactional(TxType.REQUIRES_NEW)
  public void insertReply(Message message) {
    ReplyInsertionInfo info = ReplyInsertionInfo.of(message);
    Reply reply = replyRepository.findById(info.replyId());
    reply.insertContentFromRody(info.content(), info.version());
    log.info("Reply Inserted: {}", info.content());
    alarmPublisher.publishCompletionEvent(CreationMessage.from(message));
  }

  public void insertReply(Reply reply, ReplyInsertionInfo info) {
    reply.insertContentFromRody(info.content(), info.version());
    replyRepository.save(reply);
  }

}
