package com.clody.meta.repository;

import com.clody.meta.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleMetaRepository {

  void save(Schedule schedule);

  List<Schedule> findSchedulesToNotify(LocalDateTime now);

  Page<Schedule> findByIsSentFalseAndNotificationTimeLessThanEqual(LocalDateTime now, Pageable pageable);

  void saveAll(List<Schedule> scheduleList);

}
