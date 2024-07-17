package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.presentation.reply.dto.response.ReplyResponse;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {

  private final ReplyRetriever replyRetriever;
  private final ReplyRemover replyRemover;
  private final ReplyCreator replyCreator;

  @Transactional
  public ReplyResponse readReply(int year, int month, int date) {
    Reply reply = replyRetriever.findReplyByDate(year, month, date);
    reply.readReply();
    String nickname = reply.getUser().getNickName();
    return ReplyResponse.of(nickname, reply.getContent());
  }

  public List<Reply> getRepliesByUserAndDateBetween(User user, LocalDate start, LocalDate end) {

    return replyRetriever.getRepliesByUserAndDateBetween(user, start, end);
  }

  public boolean isReplyExist(Long userId, int year, int month, int date) {
    return replyRetriever.isReplyExist(userId, year, month, date);
  }

  public void removeReply(Long userId, int year, int month, int date) {
    replyRemover.removeReplyUserIdAndDate(userId, year, month, date);
  }

  public void createStaticReply(User user, String createdDate) {
    LocalDate parsedCreatedDate = LocalDate.parse(createdDate);
    replyCreator.createStaticReply(user, parsedCreatedDate);
  }
}
