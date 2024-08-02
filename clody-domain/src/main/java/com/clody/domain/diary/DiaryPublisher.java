package com.clody.domain.diary;

import java.util.List;

public interface DiaryPublisher {

  void publishDiaryMessage(DiaryMessage message);

  void publishInitialDiaryMessage(DiaryMessage message);

  DiaryMessage convertDiariesToMessage(List<Diary> diaryList);

  void removeDiary(Long diaryId);

  boolean containsKey(Long diaryId);
}
