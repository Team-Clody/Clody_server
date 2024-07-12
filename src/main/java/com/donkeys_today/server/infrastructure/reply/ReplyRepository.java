package com.donkeys_today.server.infrastructure.reply;

import com.donkeys_today.server.domain.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("SELECT r FROM Reply r WHERE r.user.id = :userId AND YEAR(r.createdDate) = :year AND MONTH(r.createdDate) = :month")
    List<Reply> findRepliesByUserIdAndYearAndMonth(@Param("userId") Long userId, @Param("year") int year,
                                                   @Param("month") int month);
}
