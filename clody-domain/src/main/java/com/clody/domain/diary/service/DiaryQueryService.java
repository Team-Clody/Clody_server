package com.clody.domain.diary.service;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.dto.DiaryDateInfo;
import com.clody.domain.diary.dto.response.DiaryTimeInfo;
import com.clody.domain.diary.repository.DiaryRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryQueryService {

  private final DiaryRepository diaryRepository;

  public DiaryTimeInfo getCreatedTime(DiaryDateInfo info) {

    LocalDateTime localDateTime = info.parseToLocalDateTime();
    List<Diary> diaryList = diaryRepository.findTodayDiary(localDateTime);
    Diary latestDiary = Diary.getLatestDiary(diaryList);
    return DiaryTimeInfo.from(latestDiary.getCreatedAt());
  }

}
