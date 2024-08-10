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
  public List<Diary> findDiariesByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start,
      LocalDateTime end) {
    return diaryRepository.findDiariesByUserIdAndCreatedAtBetween(userId, start, end);
  }

  @Override
  public List<Diary> findDiaryByUserId(Long userId) {
    return diaryRepository.findDiariesByUserId(userId);
  }

  @Override
  public boolean existsByUser(User user) {
    return diaryRepository.existsByUser(user);
  }


}

