package com.clody.domain.alarm.strategy;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StaticAlarmDelayStrategy implements ScheduleTimeStrategy{

  @Override
  public Optional<RelativeTime> calculateAlarmDelayTime() {
    return Optional.of(new RelativeTime(10, TimeUnit.HOURS));
  }
}
