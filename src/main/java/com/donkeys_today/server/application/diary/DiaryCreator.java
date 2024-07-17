package com.donkeys_today.server.application.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.user.User;
import com.donkeys_today.server.infrastructure.diary.DiaryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryCreator {

  private final DiaryRepository diaryRepository;

  public List<Diary> saveAllDiary(User user, List<String> contents,
      LocalDateTime requestedCreatedAt) {
    List<Diary> diaryList = contents.stream()
        .map(content -> Diary.builder()
            .user(user)
            .content(content)
            .isDeleted(false)
            .createdAt(requestedCreatedAt)
            .updatedAt(requestedCreatedAt)
            .build()
        ).collect(Collectors.toUnmodifiableList());
    return diaryRepository.saveAll(diaryList);
  }

}
