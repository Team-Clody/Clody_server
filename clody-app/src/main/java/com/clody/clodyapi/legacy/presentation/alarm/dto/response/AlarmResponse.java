package com.clody.clodyapi.legacy.presentation.alarm.dto.response;

public record AlarmResponse(
    String fcmToken,
    String time,
    boolean isDiaryAlarm,
    boolean isReplyAlarm
) {
  public static AlarmResponse of(String fcmToken, String time, boolean isDiaryAlarm, boolean isReplyAlarm) {
    return new AlarmResponse(fcmToken, time, isDiaryAlarm, isReplyAlarm);
  }
}
