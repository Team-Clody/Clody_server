package com.clody.domain.diary.event.strategy;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.event.FirstDiaryCreatedEvent;
import com.clody.domain.reply.ReplyType;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class FirstDiaryStrategy implements DiaryEventStrategy {

  private static final ReplyType REPLY_TYPE = ReplyType.FIRST;

  @Override
  public boolean isApplicable(List<Diary> diaryList) {
    return diaryList.getFirst().getUser().hasNoDiary();
  }

  @Override
  public void publish(List<Diary> diaryList, ApplicationEventPublisher eventPublisher) {
    eventPublisher.publishEvent(new FirstDiaryCreatedEvent(diaryList));
  }

  @Override
  public ReplyType getReplyType() {
    return REPLY_TYPE;
  }
}
