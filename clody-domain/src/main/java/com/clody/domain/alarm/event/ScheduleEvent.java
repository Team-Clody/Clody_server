package com.clody.domain.alarm.event;

import com.clody.meta.Schedule;

public record ScheduleEvent(
    Schedule schedule
) {
  public static ScheduleEvent of(Schedule schedule) {
    return new ScheduleEvent(schedule);
  }
}
