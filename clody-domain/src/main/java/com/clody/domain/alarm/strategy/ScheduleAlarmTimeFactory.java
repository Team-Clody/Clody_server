package com.clody.domain.alarm.strategy;

import com.clody.domain.reply.ReplyType;

public interface ScheduleAlarmTimeFactory {

  ScheduleTimeStrategy getStrategy(ReplyType replyType);

}
