package com.clody.clodyapi.alarm.dto.response;

import com.clody.domain.alarm.dto.AlarmTotalInfo;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AlarmResponse(
    boolean isDiaryAlarm,
    boolean isReplyAlarm,
    String time
) {
    public static AlarmResponse of(boolean isDiaryAlarm, boolean isReplyAlarm, String time) {
        return new AlarmResponse(isDiaryAlarm, isReplyAlarm, time);
    }

    public static AlarmResponse of(AlarmTotalInfo alarmTotalInfo){
        String timeWithoutSecond = parseLocalTimeToString(alarmTotalInfo.alarmTime());
        return new AlarmResponse(alarmTotalInfo.isDiaryAlarm(), alarmTotalInfo.isReplyAlarm(), timeWithoutSecond);
    }

    private static String parseLocalTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }
}
