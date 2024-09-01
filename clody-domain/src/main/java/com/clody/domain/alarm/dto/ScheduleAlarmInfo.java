package com.clody.domain.alarm.dto;

import com.clody.domain.alarm.strategy.RelativeTime;
import com.clody.domain.reply.dto.CreationMessage;

public record ScheduleAlarmInfo(
    Long replyId,
    Long userId,
    RelativeTime delay,
    String fcmToken
) {

  public static ScheduleAlarmInfo of(CreationMessage message, RelativeTime delay, String fcmToken) {
    return new ScheduleAlarmInfo(message.replyId(), message.userId(), delay, fcmToken);
  }

}
