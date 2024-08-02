package com.clody.clodyapi.presentation.alarm.dto.request;

public record InitialDiaryAlarmRequest(
    String fcmToken,
    String time
) {
}
