package com.clody.domain.diary.event.strategy;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.event.DefaultDiaryCreatedEvent;
import com.clody.domain.reply.ReplyType;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class DefaultDiaryCreatedStrategy implements DiaryEventStrategy {

  private static final ReplyType REPLY_TYPE = ReplyType.DYNAMIC;

  @Override
  public boolean isApplicable(List<Diary> diaryList) {
    return true;
  }

  @Override
  public void publish(List<Diary> diaryList, ApplicationEventPublisher eventPublisher) {
    eventPublisher.publishEvent(new DefaultDiaryCreatedEvent(diaryList));
  }

  @Override
  public ReplyType getReplyType() {
    return REPLY_TYPE;
  }
}
