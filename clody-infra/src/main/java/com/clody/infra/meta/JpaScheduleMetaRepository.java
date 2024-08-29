package com.clody.infra.meta;

import com.clody.meta.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScheduleMetaRepository extends JpaRepository<Schedule, Long> {

  List<Schedule> findByNotificationTime(LocalDateTime notificationTime);
}
