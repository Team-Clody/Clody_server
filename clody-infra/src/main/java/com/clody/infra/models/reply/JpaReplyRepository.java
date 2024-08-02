package com.clody.infra.models.reply;

import com.clody.domain.reply.Reply;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByUserIdAndDiaryCreatedDateBetween(Long userId, LocalDate start, LocalDate end);

    List<Reply> findByUserIdAndDiaryCreatedDate(Long userId, LocalDate date);

    void deleteByUserIdAndDiaryCreatedDate(Long userId, LocalDate date);

    boolean existsByUserIdAndDiaryCreatedDate(Long userId, LocalDate date);

}
