package com.clody.infra.models.reply.repository;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.NotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReplyRepositoryAdapter implements ReplyRepository {

  private final JpaReplyRepository jpaReplyRepository;

  public ReplyRepositoryAdapter(JpaReplyRepository jpaReplyRepository) {
    this.jpaReplyRepository = jpaReplyRepository;
  }

  @Override
  public Reply findByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate) {
    return jpaReplyRepository.findByUserIdAndDiaryCreatedDate(userId, localDate).orElseThrow(
        () -> new NotFoundException(ErrorType.REPLY_NOT_FOUND)
    );
  }

  @Override
  public List<Reply> findByUserIdAndDiaryCreatedDateBetween(Long userId, LocalDate start,
      LocalDate end) {
    return jpaReplyRepository.findByUserIdAndDiaryCreatedDateBetween(userId, start, end);
  }

  @Override
  @Transactional
  public void deleteByUserIdAndDiaryCreatedDate(Long userId, LocalDate date) {
    jpaReplyRepository.deleteByUserIdAndDiaryCreatedDate(userId, date);
  }

  @Override
  public boolean existsByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate) {
    return jpaReplyRepository.existsByUserIdAndDiaryCreatedDate(userId, localDate);
  }

  @Override
  public Reply save(Reply reply) {
    return jpaReplyRepository.save(reply);
  }

  @Override
  public Reply findById(Long replyId) {
    return jpaReplyRepository.findById(replyId).orElseThrow(
        () -> new NotFoundException(ErrorType.REPLY_NOT_FOUND)
    );
  }
}
