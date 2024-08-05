package com.clody.infra.models.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DiaryRepositoryAdapter implements DiaryRepository {

  private final JpaDiaryRepository diaryRepository;

  public DiaryRepositoryAdapter(JpaDiaryRepository diaryRepository) {
    this.diaryRepository = diaryRepository;
  }

  @Override
  public List<Diary> saveAll(List<Diary> diaries) {
    return diaryRepository.saveAll(diaries);
  }

  @Override
  public List<Diary> findDiariesByUserAndCreatedAtBetween(User user, LocalDateTime start,
      LocalDateTime end) {
    return diaryRepository.findDiariesByUserAndCreatedAtBetween(user, start, end);
  }

  @Override
  public boolean existsByUser(User user) {
    return diaryRepository.existsByUser(user);
  }
}

