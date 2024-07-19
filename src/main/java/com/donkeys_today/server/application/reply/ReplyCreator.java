package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
import com.donkeys_today.server.support.config.ReplyProperty;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyCreator {

  private final ReplyRepository replyRepository;
  private final ReplyProperty replyProperty;

  public Reply createStaticReply(User user, LocalDate createdDate) {
    log.info(replyProperty.getContent());
    Reply reply = Reply.createStaticReply(user, replyProperty.getContent(), createdDate);
    return replyRepository.save(reply);
  }

}
