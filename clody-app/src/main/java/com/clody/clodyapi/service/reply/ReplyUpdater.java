package com.clody.clodyapi.service.reply;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyUpdater {

  private final ReplyRepository replyRepository;

  public void readReply(Reply reply) {
    reply.readReply();
    replyRepository.save(reply);
  }
}
