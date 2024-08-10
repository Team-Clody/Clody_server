package com.clody.domain.diary.event.publisher;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.event.DiaryDeletionEvent;
import com.clody.domain.diary.event.strategy.DiaryEventStrategy;
import com.clody.domain.diary.event.strategy.DiaryStrategyManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryEventPublisherImpl implements DiaryEventPublisher {

  private final ApplicationEventPublisher eventPublisher;
  private final DiaryStrategyManager strategyManager;

  public void publish(List<Diary> diaryList) {
    DiaryEventStrategy strategy = strategyManager.findStrategy(diaryList);
    strategy.publish(diaryList, eventPublisher);
  }

  @Override
  public void raiseDeletion(DiaryDeletionEvent diaryDeletionEvent) {
    eventPublisher.publishEvent(diaryDeletionEvent);
  }
}
