package com.clody.clodyapi.alarm.dto.request;

import com.clody.domain.alarm.dto.UpdateAlarmCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalTime;

public record AlarmUpdateRequest(
    @Schema(description = "다이어리 알람 수신 동의 여부, Nullable", example = "true")
    Boolean isDiaryAlarm,
    @Schema(description = "답장 알람 수신 동의 여부, Nullable", example = "true")
    Boolean isReplyAlarm,
    @Schema(description = "FCM Token, Nullable", example = "toekawneklrna")
    String fcmToken,
    @Schema(description = "알람 시간, Nullable", example = "12:00")
    LocalTime time
) {
  public static UpdateAlarmCommand toUpdateAlarmCommand(AlarmUpdateRequest alarmUpdateRequest){
    return new UpdateAlarmCommand(alarmUpdateRequest.isDiaryAlarm(), alarmUpdateRequest.isReplyAlarm(), alarmUpdateRequest.time(), alarmUpdateRequest.fcmToken());
  }
}
