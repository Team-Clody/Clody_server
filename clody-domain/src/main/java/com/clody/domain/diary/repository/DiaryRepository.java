package com.clody.domain.diary.repository;

import com.clody.domain.diary.Diary;
import com.clody.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository {

  List<Diary> saveAll(List<Diary> diaryList);

  List<Diary> findDiariesByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

  boolean existsByUser(User user);
}
