package com.clody.infra.models.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.user.User;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.NotFoundException;
import com.clody.support.security.util.JwtUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
    return diaryRepository.findDiariesByUserIdAndCreatedAtBetween(userId, start, end)
        .orElseThrow(() -> new NotFoundException(ErrorType.DIARY_MESSAGE_NOT_FOUND));
  }

  @Override
  public List<Diary> findDiaryByUserId(Long userId) {
    return diaryRepository.findDiariesByUserId(userId);
  }

  @Override
  public boolean existsByUser(User user) {
    return diaryRepository.existsByUser(user);
  }

  @Override
  public List<Diary> findTodayDiary(LocalDateTime start) {
    Long userId = JwtUtil.getLoginMemberId();

    LocalDateTime end = start.plusDays(1);

    return findDiariesByUserIdAndCreatedAtBetween(userId, start, end).stream()
        .filter(diary -> !diary.checkDiaryDeleted())
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public boolean findIfUserHasDeletedDiary(LocalDateTime start) {
    Long userId = JwtUtil.getLoginMemberId();
    LocalDateTime end = start.plusDays(1);
    return diaryRepository.existsByUserIdAndCreatedAtBetweenAndIsDeletedTrue(userId, start, end);
  }
}

