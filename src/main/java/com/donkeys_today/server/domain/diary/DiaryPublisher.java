package com.donkeys_today.server.domain.diary;

import com.donkeys_today.server.application.diary.dto.DiaryMessage;
import java.util.List;

public interface DiaryPublisher {

  void publishDiaryMessage(DiaryMessage message);

  void publishInitialDiaryMessage(DiaryMessage message);

  DiaryMessage convertDiariesToMessage(List<Diary> diaryList);
}
