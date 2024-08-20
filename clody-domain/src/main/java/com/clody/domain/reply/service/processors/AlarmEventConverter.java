package com.clody.domain.reply.service.processors;

import com.clody.domain.alarm.event.CompletionEvent;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventConverter {

  private final ReplyRepository replyRepository;

  public CompletionEvent convertToCompletionEvent(Long replyId) {
    Reply reply = replyRepository.findById(replyId);
    return CompletionEvent.of(reply.getUser(),replyId);
  }

}
