package com.clody.domain.alarm.event;

import com.clody.domain.reply.dto.CreationMessage;

public interface AlarmPublisher {

  void publishCompletionEvent(CreationMessage creationMessage);

}
