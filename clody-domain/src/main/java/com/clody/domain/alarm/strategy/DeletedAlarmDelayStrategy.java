package com.clody.domain.alarm.strategy;

import java.util.Optional;

public class DeletedAlarmDelayStrategy implements ScheduleTimeStrategy{

  @Override
  public Optional<RelativeTime> calculateAlarmDelayTime() {
    return Optional.empty();
  }
}
