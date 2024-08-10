package com.clody.domain.diary.event.strategy;

import com.clody.domain.diary.Diary;
import com.clody.domain.reply.ReplyType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DiaryStrategyManager {

  private final List<DiaryEventStrategy> diaryEventStrategyList = new ArrayList<>();

  public DiaryStrategyManager() {
    this.diaryEventStrategyList.add(new ProfanityDiaryStrategy());
    this.diaryEventStrategyList.add(new FirstDiaryStrategy());
    this.diaryEventStrategyList.add(new DefaultDiaryCreatedStrategy());
  }

  public ReplyType determineReplyType(List<Diary> diaryList) {
    for (DiaryEventStrategy strategy : diaryEventStrategyList) {
      if (strategy.isApplicable(diaryList)) {
        return strategy.getReplyType();
      }
    }
    return ReplyType.DYNAMIC;
  }

  public DiaryEventStrategy findStrategy(List<Diary> diaryList){
    for (DiaryEventStrategy strategy : diaryEventStrategyList) {
      if (strategy.isApplicable(diaryList)) {
        return strategy;
      }
    }
    return new DefaultDiaryCreatedStrategy();
  }
}
