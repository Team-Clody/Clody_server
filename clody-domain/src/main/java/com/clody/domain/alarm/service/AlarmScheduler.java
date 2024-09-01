package com.clody.domain.alarm.service;

import com.clody.domain.alarm.dto.ScheduleAlarmInfo;

public interface AlarmScheduler {

  void scheduleReplyPushAlarm(ScheduleAlarmInfo alarmInfo);
}
