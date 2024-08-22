package com.clody.domain.alarm.dto;

import java.time.LocalTime;

public record UpdateAlarmCommand(
    Boolean isDiaryAlarm,
    Boolean isReplyAlarm,
    LocalTime time,
    String fcmToken
) {
}
