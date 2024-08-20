package com.clody.domain.alarm.event;

public interface AlarmPublisher {

  void publishCompletionEvent(CompletionEvent completionEvent);
}
