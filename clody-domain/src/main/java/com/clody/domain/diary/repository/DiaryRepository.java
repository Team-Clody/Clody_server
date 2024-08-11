package com.clody.domain.diary.repository;

import com.clody.domain.diary.Diary;
import com.clody.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository {

  List<Diary> saveAll(List<Diary> diaryList);

  List<Diary> findDiariesByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

  List<Diary> findDiaryByUserId(Long userId);

  boolean existsByUser(User user);

  List<Diary> findTodayDiary(LocalDateTime localDateTime);
}
