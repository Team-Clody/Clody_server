package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.application.diary.dto.DiaryMessage;
import com.donkeys_today.server.domain.diary.Diary;
import java.util.List;

public interface DiaryPublisher {

  void publishDiaryMessage(DiaryMessage message);

  DiaryMessage convertDiariesToMessage(List<Diary> diaryList);

}
