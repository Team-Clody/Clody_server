package com.clody.clodyapi.service.reply;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.user.User;
import com.clody.support.config.ReplyProperty;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyCreator {

  private final ReplyRepository replyRepository;
  private final ReplyProperty replyProperty;


  public Reply createStaticReply(User user , LocalDate createdDate) {
    Reply reply = Reply.createStaticReply(user, replyProperty.getContent(), createdDate);
    return replyRepository.save(reply);
  }

}
