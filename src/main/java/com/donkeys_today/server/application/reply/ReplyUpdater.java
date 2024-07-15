package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
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
