package com.clody.clodyapi.service.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
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
