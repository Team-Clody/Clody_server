package com.clody.clodyapi.service.diary;

import com.clody.clodyapi.presentation.diary.dto.request.DiaryRequest;
import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryPolicy {

  private final ProfanityFilter profanityFilter;
  private final DiaryRepository diaryRepository;
  private final DiaryRetriever diaryRetriever;

  public boolean checkUserInitialDiary(User user) {
    return diaryRepository.existsByUser(user);
  }

  public boolean containsProfanity(List<String> content) {
    if (profanityFilter.containsProfanity(content)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean hasDeletedDiary(User user, String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(date, formatter);
    LocalDateTime localDateTime = localDate.atStartOfDay();
    List<Diary> foundDiary = diaryRetriever.getTodayDiariesByUser(user, localDateTime);
    return foundDiary.stream().anyMatch(Diary::isDeleted);
  }

  public void updateDeletedDiary(User user, DiaryRequest request) {

    List<Diary> newDiaries = request.content().stream()
        .map(content -> Diary.builder()
            .isDeleted(false)
            .content(content)
            .user(user)
            .updatedAt(LocalDateTime.now())
            .build())
        .collect(Collectors.toUnmodifiableList());
    diaryRepository.saveAll(newDiaries);
  }
}
