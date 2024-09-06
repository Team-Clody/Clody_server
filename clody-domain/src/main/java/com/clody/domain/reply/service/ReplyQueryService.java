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

    //처음 읽은 시점에는 isRead == false;
    //TODO 굉장히 절차 지향적인 로직 같습니다! 런칭 이후 도메인 로직 중심으로 개편이 필요할거 같습니다.
    ReplyResponse response = ReplyResponse.from(reply);
    reply.readReply();

    replyRepository.save(reply);
    return response;
  }

}
