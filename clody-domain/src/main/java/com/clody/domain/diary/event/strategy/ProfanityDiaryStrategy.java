package com.clody.domain.diary.event.strategy;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.event.ProfanityDetectedDiaryEvent;
import com.clody.domain.reply.ReplyType;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public class ProfanityDiaryStrategy implements DiaryEventStrategy {

  private static final ReplyType REPLY_TYPE = ReplyType.STATIC;

  @Override
  public boolean isApplicable(List<Diary> diaryList) {
    return diaryList.stream().anyMatch(Diary::isContainsProfanity);
  }

  @Override
  public void publish(List<Diary> diaryList, ApplicationEventPublisher eventPublisher) {
    eventPublisher.publishEvent(new ProfanityDetectedDiaryEvent(diaryList));
  }

  @Override
  public ReplyType getReplyType() {
    return REPLY_TYPE;
  }
}
