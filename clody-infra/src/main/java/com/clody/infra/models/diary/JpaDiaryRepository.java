package com.clody.infra.models.diary;

import com.clody.domain.diary.Diary;
import com.clody.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDiaryRepository extends JpaRepository<Diary, Long> {

  boolean existsByUser(User user);

  Optional<List<Diary>> findDiariesByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

  List<Diary> findDiariesByUserId(Long userId);

  boolean findDiariesByUserIdAndCreatedAtBetweenAndDeletedTrue(Long userId, LocalDateTime start, LocalDateTime end);
}
