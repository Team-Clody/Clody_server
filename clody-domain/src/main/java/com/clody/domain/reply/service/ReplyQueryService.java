package com.clody.domain.reply.service;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.dto.ReplyResponse;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyQueryService {

  private final ReplyRepository replyRepository;

  public ReplyResponse retrieveReplyByDate(LocalDate date){
    Long userId = JwtUtil.getLoginMemberId();
    Reply reply = replyRepository.findByUserIdAndDiaryCreatedDate(userId, date);
    reply.readReply();
    replyRepository.save(reply);
    return ReplyResponse.from(reply);
  }

}
