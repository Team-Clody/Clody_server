package com.clody.meta.repository;

import com.clody.meta.Schedule;
import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleMetaRepository {

  void save(Schedule schedule);

  List<Schedule> findSchedulesToNotify(LocalDateTime now);
}
