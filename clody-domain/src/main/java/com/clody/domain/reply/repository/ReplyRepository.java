package com.clody.domain.reply.repository;

import com.clody.domain.reply.Reply;
import java.time.LocalDate;
import java.util.List;

public interface ReplyRepository {

  List<Reply> findByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate);

  List<Reply> findByUserIdAndDiaryCreatedDateBetween(Long userId, LocalDate start, LocalDate end);

  void deleteByUserIdAndDiaryCreatedDate(Long userId, LocalDate date);

  boolean existsByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate);

  Reply save(Reply reply);
}
