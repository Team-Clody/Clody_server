package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DiaryRetriever {

  private final DiaryRepository diaryRepository;

  public List<Diary> getTodayDiariesByUser(User user, LocalDateTime currentTime){
    LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime end = currentTime;
    return diaryRepository.findDiariesByUserAndCreatedAtBetween(user, start, end);
  }

  public List<Diary> getDiariesByUserAndDateBetween(User user, LocalDateTime start, LocalDateTime end) {

    return diaryRepository.findDiariesByUserAndCreatedAtBetween(user, start, end);
  }
}
