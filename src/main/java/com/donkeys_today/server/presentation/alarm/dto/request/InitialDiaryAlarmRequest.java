package com.donkeys_today.server.presentation.alarm.dto.request;

public record InitialDiaryAlarmRequest(
    String fcmToken,
    String time
) {
}
