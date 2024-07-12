package com.donkeys_today.server.domain.reply;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByUserIdAndCreatedDateBetween(Long userId, LocalDate start, LocalDate end);

}
