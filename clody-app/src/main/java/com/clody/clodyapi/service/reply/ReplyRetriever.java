package com.clody.clodyapi.service.reply;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.reply.ReplyException;
import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.domain.user.User;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyRetriever {

  private final ReplyRepository replyRepository;

  public Reply findReplyByDate(int year, int month, int date) {
    LocalDate localDate = LocalDate.of(year, month, date);
    Long userId = JwtUtil.getLoginMemberId();
    List<Reply> replies = replyRepository.findByUserIdAndDiaryCreatedDate(userId, localDate);

    return Optional.of(replies)
        .filter(list -> !list.isEmpty())
        .map(list -> {
          if (list.size() > 1) {
            throw new ReplyException(ErrorType.DUPLICATE_REPLY);
          }
          return list.getFirst();
        })
        .orElseThrow(() -> new ReplyException(ErrorType.REPLY_NOT_FOUND));
  }

  public List<Reply> getRepliesByUserAndDateBetween(User user, LocalDate start, LocalDate end) {
    return replyRepository.findByUserIdAndDiaryCreatedDateBetween(user.getId(), start, end);
  }

  public boolean isReplyExist(Long userId, int year, int month, int date) {
    LocalDate localDate = LocalDate.of(year, month, date);
    return replyRepository.existsByUserIdAndDiaryCreatedDate(userId, localDate);
  }
}
