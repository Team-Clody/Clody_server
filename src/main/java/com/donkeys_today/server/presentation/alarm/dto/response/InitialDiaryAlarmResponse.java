package com.donkeys_today.server.presentation.alarm.dto.response;

public record InitialDiaryAlarmResponse(
    String fcmToken,
    String time
) {
  public static InitialDiaryAlarmResponse of(String fcmToken, String time) {
    return new InitialDiaryAlarmResponse(fcmToken, time);
  }
}
