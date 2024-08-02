package com.clody.infra.models.reply;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.repository.ReplyRepository;
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
  public List<Reply> findByUserIdAndDiaryCreatedDate(Long userId, LocalDate localDate) {
    return jpaReplyRepository.findByUserIdAndDiaryCreatedDate(userId, localDate);
  }

  @Override
  public List<Reply> findByUserIdAndDiaryCreatedDateBetween(Long userId, LocalDate start,
      LocalDate end) {
    return jpaReplyRepository.findByUserIdAndDiaryCreatedDateBetween(userId, start, end);
  }

  @Override
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
}
