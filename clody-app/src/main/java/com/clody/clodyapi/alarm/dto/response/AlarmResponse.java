package com.clody.clodyapi.alarm.dto.response;

import com.clody.domain.alarm.dto.AlarmTotalInfo;
import java.time.LocalTime;

public record AlarmResponse(
    boolean isDiaryAlarm,
    boolean isReplyAlarm,
    LocalTime time
) {
    public static AlarmResponse of(boolean isDiaryAlarm, boolean isReplyAlarm, LocalTime time) {
        return new AlarmResponse(isDiaryAlarm, isReplyAlarm, time);
    }

    public static AlarmResponse of(AlarmTotalInfo alarmTotalInfo){
        return new AlarmResponse(alarmTotalInfo.isDiaryAlarm(), alarmTotalInfo.isReplyAlarm(), alarmTotalInfo.alarmTime());
    }
}
