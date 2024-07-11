package com.donkeys_today.server.presentation.alarm.dto.request;

public record AlarmRequest(
    String fcmToken,
    String time,
    Boolean isDiaryAlarm,
    Boolean isReplyAlarm
) {

}
