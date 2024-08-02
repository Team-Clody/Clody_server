package com.clody.clodyapi.service.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiaryRetriever {

  private final DiaryRepository diaryRepository;

  public List<Diary> getTodayDiariesByUser(User user, LocalDateTime currentTime) {
    LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime end = currentTime;
    return diaryRepository.findDiariesByUserAndCreatedAtBetween(user, start, end);
  }


  public List<Diary> getDiariesByUserAndDateBetween(User user, LocalDateTime start, LocalDateTime end) {
    return diaryRepository.findDiariesByUserAndCreatedAtBetween(user, start, end);
  }

  public List<Diary> findDiariesNotDeleted(List<Diary> diaries) {
    return diaries.stream().filter(diary -> !diary.isDeleted())
        .collect(Collectors.toUnmodifiableList());
  }
}
