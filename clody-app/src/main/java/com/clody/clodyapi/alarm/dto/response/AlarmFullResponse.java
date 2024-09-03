package com.clody.clodyapi.alarm.dto.response;

import com.clody.domain.alarm.dto.AlarmTotalInfo;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AlarmFullResponse(
    boolean isDiaryAlarm,
    boolean isReplyAlarm,
    String time,
    String fcmToken
) {

  public static AlarmFullResponse of(boolean isDiaryAlarm, boolean isReplyAlarm, String time,
      String fcmToken) {
    return new AlarmFullResponse(isDiaryAlarm, isReplyAlarm, time, fcmToken);
  }

  public static AlarmFullResponse parseFromAlarmInfo(AlarmTotalInfo alarmTotalInfo) {
      String timeWithoutSeconds = parseLocalTimeToString(alarmTotalInfo.alarmTime());
    return AlarmFullResponse.of(alarmTotalInfo.isDiaryAlarm(), alarmTotalInfo.isReplyAlarm(),
        timeWithoutSeconds, alarmTotalInfo.fcmToken());
  }

  private static String parseLocalTimeToString(LocalTime time) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    return time.format(formatter);
  }
}
