package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryRemover {

  private final DiaryRepository diaryRepository;

  public void removeDiarySoft(List<Diary> diaryList) {
    diaryList.forEach(Diary::deleteDiary);
    diaryRepository.saveAll(diaryList);
  }
}
