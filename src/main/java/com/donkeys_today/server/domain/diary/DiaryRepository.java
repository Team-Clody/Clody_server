package com.donkeys_today.server.domain.diary;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d " +
            "FROM Diary d WHERE d.user.id = :userId AND YEAR(d.createdAt) = :year AND MONTH(d.createdAt) = :month")
    List<Diary> findContentsByUserIdAndYearAndMonth(@Param("userId") Long userId, @Param("year") int year,
                                                    @Param("month") int month);

    @Query("SELECT d " +
            "FROM Diary d WHERE d.user.id = :userId AND YEAR(d.createdAt) = :year AND MONTH(d.createdAt) = :month AND DAY(d.createdAt) = :day")
    List<Diary> findContentsByUserIdAndYearAndMonthAndDay(@Param("userId") Long userId, @Param("year") int year,
                                                          @Param("month") int month, @Param("day") int day);
}
