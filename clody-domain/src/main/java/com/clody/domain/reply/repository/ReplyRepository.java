package com.clody.domain.reply.repository;

import com.clody.domain.reply.Reply;
import java.time.LocalDate;
import java.util.List;

public interface ReplyRepository {

  void deleteByUserIdAndDiaryCreatedDate(Long userId, LocalDate date);

  boolean existsByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate);

  List<Reply> findByUserIdAndDiaryCreatedDateBetween(Long userId, LocalDate start, LocalDate end);

  Reply findByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate);

  Reply findById(Long replyId);

  Reply save(Reply reply);

  void delete(Reply reply);

  Reply findByReplyId(Long replyId);

  boolean existsDeletedReplyByUserIdAndDiaryCreatedDate(Long userId, LocalDate date);
}
