package com.clody.domain.diary.event.publisher;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.event.DiaryDeletionEvent;
import java.util.List;

public interface DiaryEventPublisher {

  void publish(List<Diary> diaryEvent);

  void raiseDeletion(DiaryDeletionEvent diaryDeletionEvent);
}
