package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.application.auth.JwtUtil;
import com.donkeys_today.server.domain.reply.Reply;
import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.NotFoundException;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyRetriever {

  private final ReplyRepository replyRepository;

  public Reply findReplyByDate(int year, int month, int date) {
    LocalDate localDate = LocalDate.of(year, month, date);
    Long userId = JwtUtil.getLoginMemberId();
    return replyRepository.findByUserIdAndDiaryCreatedDate(userId, localDate).orElseThrow(
        () -> new NotFoundException(ErrorType.REPLY_NOT_FOUND)
    );
  }

  public boolean isReplyExist(Long userId, int year, int month, int date) {
    LocalDate localDate = LocalDate.of(year, month, date);
    return replyRepository.existsByUserIdAndDiaryCreatedDate(userId, localDate);
  }
}
