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

  @Transactional
  public ReplyResponse readReply(String year, String month, String date) {
    Reply reply = replyRetriever.findReplyByDate(year, month, date);
    reply.readReply();
    return ReplyResponse.of(reply.getContent());
  }
}
