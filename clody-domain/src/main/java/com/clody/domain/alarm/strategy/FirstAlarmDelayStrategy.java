package com.clody.domain.alarm.strategy;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class FirstAlarmDelayStrategy implements ScheduleTimeStrategy{

  @Override
  public Optional<RelativeTime> calculateAlarmDelayTime() {
    return Optional.of(new RelativeTime(10, TimeUnit.MINUTES));
  }

}
