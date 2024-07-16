package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
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
    }else{
      return false;
    }
  }

  public boolean hasDeletedDiary(User user, String time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate localDate = LocalDate.parse(time, formatter);
    LocalDateTime localDateTime = localDate.atStartOfDay();
    List<Diary> foundDiary = diaryRetriever.getNotDeletedDiariesByUserAndDateBetween(user, localDateTime);
    return foundDiary.stream().anyMatch(Diary::isDeleted);
  }

  public void updateDeletedDiary(User user, DiaryRequest request) {
    List<Diary> foundDiary = diaryRetriever.getNotDeletedDiariesByUserAndDateBetween(user,
        LocalDateTime.now());
    diaryRepository.deleteAll(foundDiary);
    List<Diary> newDiaries = request.content().stream()
        .map(content -> Diary.builder()
            .isDeleted(false)
            .content(content)
            .user(user)
            .build()
        ).collect(Collectors.toUnmodifiableList());
    diaryRepository.saveAll(newDiaries);
  }
}
