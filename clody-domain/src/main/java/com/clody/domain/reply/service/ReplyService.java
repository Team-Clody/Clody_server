package com.clody.domain.reply.service;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.ReplyInsertionInfo;
import com.clody.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

  private final ReplyRepository replyRepository;

  public void insertReply(ReplyInsertionInfo info) {
    Reply reply = replyRepository.findById(info.replyId());
    reply.insertContentFromRody(info.content());
    replyRepository.save(reply);
  }
}
