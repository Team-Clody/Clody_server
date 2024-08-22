package com.clody.clodyapi.alarm.dto.request;

import com.clody.domain.alarm.dto.UpdateAlarmCommand;
import java.time.LocalTime;

public record AlarmUpdateRequest(
    Boolean isDiaryAlarm,
    Boolean isReplyAlarm,
    String fcmToken,
    LocalTime time
) {
  public static UpdateAlarmCommand toUpdateAlarmCommand(AlarmUpdateRequest alarmUpdateRequest){
    return new UpdateAlarmCommand(alarmUpdateRequest.isDiaryAlarm(), alarmUpdateRequest.isReplyAlarm(), alarmUpdateRequest.time(), alarmUpdateRequest.fcmToken());
  }
}
