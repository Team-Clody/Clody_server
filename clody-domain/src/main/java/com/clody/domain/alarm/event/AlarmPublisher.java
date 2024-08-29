package com.clody.domain.alarm.event;

public interface AlarmPublisher {

  void publishCompletionEvent(ScheduleEvent scheduleEvent);
}
