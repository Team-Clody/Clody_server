package com.clody.clodyapi.legacy.presentation.alarm.dto.request;

public record InitialDiaryAlarmRequest(
    String fcmToken,
    String time
) {
}
