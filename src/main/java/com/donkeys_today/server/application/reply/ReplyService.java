package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.presentation.reply.dto.response.ReplyResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

  private final ReplyRetriever replyRetriever;
  private final ReplyUpdater replyUpdater;
  private final ReplyRemover replyRemover;

  @Transactional
  public ReplyResponse readReply(int year, int month, int date) {
    Reply reply = replyRetriever.findReplyByDate(year, month, date);
    reply.readReply();
    return ReplyResponse.of(reply.getContent());
  }

  public boolean isReplyExist(Long userId, int year, int month, int date) {
    return replyRetriever.isReplyExist(userId, year, month, date);
  }

  public void removeReply(Long userId, int year, int month, int date) {
    replyRemover.removeReplyUserIdAndDate(userId, year, month, date);
  }
}
