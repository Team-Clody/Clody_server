package com.clody.domain.alarm.strategy;

import java.util.Optional;

public interface ScheduleTimeStrategy {
  Optional<RelativeTime> calculateAlarmDelayTime();
}
