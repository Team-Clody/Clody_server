package com.clody.clodyapi.service.reply;

import com.clody.clodyapi.presentation.reply.dto.response.ReplyResponse;
import com.clody.domain.reply.Reply;
import com.clody.domain.user.User;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    return serializeReply(reply);
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

//  public Reply createStaticReply(User user, String createdDate) {
//    LocalDate parsedCreatedDate = LocalDate.parse(createdDate);
//    replyCreator.createStaticReply(user, parsedCreatedDate);
//    return replyCreator.createStaticReply(user, parsedCreatedDate);
//  }

  private ReplyResponse serializeReply(Reply reply) {
    String nickName = reply.getUser().getNickName();
    String content = reply.getContent();
    int month = reply.getDiaryCreatedDate().getMonthValue();
    int date = reply.getDiaryCreatedDate().getDayOfMonth();
    return ReplyResponse.of(nickName, content, month, date);
  }
}
