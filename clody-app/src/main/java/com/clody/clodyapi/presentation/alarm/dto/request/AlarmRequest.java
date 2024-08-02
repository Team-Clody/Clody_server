package com.clody.clodyapi.presentation.alarm.dto.request;

public record AlarmRequest(
    String fcmToken,
    String time,
    Boolean isDiaryAlarm,
    Boolean isReplyAlarm
) {

}
