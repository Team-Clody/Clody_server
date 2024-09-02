package com.clody.domain.alarm.strategy;

import com.clody.domain.reply.ReplyType;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTimeDelayStrategyFactory implements ScheduleAlarmTimeFactory {

  private Map<ReplyType, ScheduleTimeStrategy> strategyMap = new HashMap<>();

  public ScheduleTimeDelayStrategyFactory() {
    strategyMap.put(ReplyType.FIRST, new FirstAlarmDelayStrategy());
    strategyMap.put(ReplyType.DYNAMIC, new DynamicAlarmDelayStrategy());
    strategyMap.put(ReplyType.STATIC, new StaticAlarmDelayStrategy());
    strategyMap.put(ReplyType.DELETED, new DeletedAlarmDelayStrategy());
  }

  public ScheduleTimeStrategy getStrategy( ReplyType type){
    return strategyMap.get(type);
  }
}
