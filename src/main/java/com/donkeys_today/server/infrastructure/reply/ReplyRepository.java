package com.donkeys_today.server.infrastructure.reply;

import com.donkeys_today.server.domain.reply.Reply;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByUserIdAndDiaryCreatedDateBetween(Long userId, LocalDate start, LocalDate end);

}
