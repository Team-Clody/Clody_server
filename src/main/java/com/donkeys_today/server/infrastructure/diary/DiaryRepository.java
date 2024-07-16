package com.donkeys_today.server.infrastructure.diary;

import com.donkeys_today.server.domain.diary.Diary;
import com.donkeys_today.server.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

  boolean existsByUser(User user);

  List<Diary> findByUserAndIsDeletedFalseAndCreatedAtBetween(User user, LocalDateTime start, LocalDateTime end);

}
