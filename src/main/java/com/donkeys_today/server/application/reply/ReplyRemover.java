package com.donkeys_today.server.application.reply;

import com.donkeys_today.server.infrastructure.reply.ReplyRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyRemover {

  private final ReplyRepository replyRepository;

  public void removeReplyUserIdAndDate(Long userId, int year, int month, int date) {
    LocalDate deleteDay = LocalDate.of(year,month,date);
    replyRepository.deleteByUserIdAndDiaryCreatedDate(userId, deleteDay);
  }
}
