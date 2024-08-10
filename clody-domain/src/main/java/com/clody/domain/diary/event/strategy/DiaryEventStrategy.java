package com.clody.domain.diary.event.strategy;

import com.clody.domain.diary.Diary;
import com.clody.domain.reply.ReplyType;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;

public interface DiaryEventStrategy {

  boolean isApplicable(List<Diary> diaryList);

  void publish(List<Diary> diaryList, ApplicationEventPublisher eventPublisher);

  ReplyType getReplyType();
}
