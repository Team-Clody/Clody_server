package com.clody.infra.meta;

import com.clody.meta.Schedule;
import com.clody.meta.repository.ScheduleMetaRepository;
import com.clody.infra.config.TransactionManagerConfig;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScheduleMetaRepositoryAdapter implements ScheduleMetaRepository {

  private final JpaScheduleMetaRepository jpaScheduleMetaRepository;

  @Override
  @Transactional(TransactionManagerConfig.META_TRANSACTION_MANAGER)
  public void save(Schedule schedule) {
    jpaScheduleMetaRepository.save(schedule);
  }

  @Override
  public List<Schedule> findSchedulesToNotify(LocalDateTime now) {
    return List.of();
  }
}
