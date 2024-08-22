package com.clody.clodyapi.alarm.dto.response;

import com.clody.domain.alarm.dto.AlarmTotalInfo;
import java.time.LocalTime;

public record AlarmFullResponse(
    boolean isDiaryAlarm,
    boolean isReplyAlarm,
    LocalTime time,
    String fcmToken
) {
    public static AlarmFullResponse of(boolean isDiaryAlarm, boolean isReplyAlarm, LocalTime time, String fcmToken) {
        return new AlarmFullResponse(isDiaryAlarm, isReplyAlarm, time, fcmToken);
    }

    public static AlarmFullResponse parseFromAlarmInfo(AlarmTotalInfo alarmTotalInfo){
        return AlarmFullResponse.of(alarmTotalInfo.isDiaryAlarm(), alarmTotalInfo.isReplyAlarm(), alarmTotalInfo.alarmTime(), alarmTotalInfo.fcmToken());
    }

    public static AlarmTotalInfo toAlarmTotalInfo(AlarmFullResponse alarmFullResponse){
        return AlarmTotalInfo.of(alarmFullResponse.fcmToken(), alarmFullResponse.time(), alarmFullResponse.isDiaryAlarm(), alarmFullResponse.isReplyAlarm());
    }
}
