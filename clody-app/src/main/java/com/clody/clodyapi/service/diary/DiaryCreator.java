package com.clody.clodyapi.service.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.user.User;
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
