package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyRetriever {

  private final ReplyRepository replyRepository;

  public Reply findReplyByDate(String year, String month, String date) {
    LocalDate localDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month),
        Integer.parseInt(date));
    Long userId = JwtUtil.getLoginMemberId();
    return replyRepository.findByUserIdAndDiaryCreatedDate(userId, localDate).orElseThrow(
        () -> new NotFoundException(ErrorType.REPLY_NOT_FOUND)
    );
  }

  public List<Reply> getRepliesByUserAndDateBetween(User user, LocalDate start, LocalDate end) {

    return replyRepository.findByUserIdAndDiaryCreatedDateBetween(user.getId(), start, end);
  }

  public boolean isReplyExist(Long userId, int year, int month, int date) {
    LocalDate localDate = LocalDate.of(year, month, date);
    return replyRepository.existsByUserIdAndDiaryCreatedDate(userId, localDate);
  }
}
