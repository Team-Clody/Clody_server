package com.clody.domain.alarm.dto;

import com.clody.domain.alarm.Alarm;
import java.time.LocalTime;

public record AlarmTotalInfo(
    String fcmToken,
    LocalTime alarmTime,
    boolean isDiaryAlarm,
    boolean isReplyAlarm
) {
    public static AlarmTotalInfo of(String fcmToken, LocalTime alarmTime, boolean isDiaryAlarm, boolean isReplyAlarm) {
        return new AlarmTotalInfo(fcmToken, alarmTime, isDiaryAlarm, isReplyAlarm);
    }

    public static AlarmTotalInfo of(Alarm alarm){
        return new AlarmTotalInfo(alarm.getFcmToken(), alarm.getTime(), alarm.isDiaryAlarm(), alarm.isReplyAlarm());
    }
}
