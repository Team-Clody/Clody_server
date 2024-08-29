package com.clody.clodybatch.service;

import com.clody.meta.Schedule;
import com.clody.meta.repository.ScheduleMetaRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleMetaRepository scheduleMetaRepository;

  public List<Schedule> findSchedulesToNotify(LocalDateTime time) {
    LocalDateTime refinedTime = time.truncatedTo(ChronoUnit.SECONDS);
    return scheduleMetaRepository.findSchedulesToNotify(refinedTime).stream()
        .filter(schedule -> !schedule.isNotificationSent())
        .collect(Collectors.toUnmodifiableList());
  }

  public void updateScheduleAsSent(Schedule schedule) {
    schedule.notifySent();
    scheduleMetaRepository.save(schedule);
  }
}
