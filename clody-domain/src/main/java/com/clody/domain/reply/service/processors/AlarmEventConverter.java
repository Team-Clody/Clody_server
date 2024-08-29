package com.clody.domain.reply.service.processors;

import com.clody.meta.Schedule;
import com.clody.domain.alarm.event.ScheduleEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmEventConverter {

  public ScheduleEvent convertToCompletionEvent(Schedule schedule) {
    return new ScheduleEvent(schedule);
  }
}
