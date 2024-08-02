package com.clody.infra.models.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.diary.repository.DiaryRepository;
import com.clody.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDiaryRepository extends JpaRepository<Diary, Long>, DiaryRepository {

  boolean existsByUser(User user);

  List<Diary> findDiariesByUserAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

  List<Diary> saveAll(List<Diary> diaryList);

}
