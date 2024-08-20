package com.clody.domain.diary.event.strategy;

import com.clody.domain.diary.Diary;
import com.clody.domain.reply.ReplyType;
import com.clody.domain.reply.repository.ReplyRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@RequiredArgsConstructor
public class DeletedDiaryStrategy implements DiaryEventStrategy {

  private final ReplyRepository replyRepository;

  @Override
  public boolean isApplicable(List<Diary> diaryList) {
    LocalDate date = diaryList.getFirst().getCreatedAt().toLocalDate();
    Long userId = diaryList.getFirst().getUser().getId();
    //삭제된 다이어리가 존재하는지 확인
    return replyRepository.existsDeletedReplyByUserIdAndDiaryCreatedDate(userId, date);
  }

  @Override
  public void publish(List<Diary> diaryList, ApplicationEventPublisher eventPublisher) {
    // doing nothing
  }

  @Override
  public ReplyType getReplyType() {
    return ReplyType.DELETED;
  }
}
