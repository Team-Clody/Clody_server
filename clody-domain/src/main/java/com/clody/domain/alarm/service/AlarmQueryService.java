package com.clody.domain.alarm.service;

import com.clody.domain.alarm.Alarm;
import com.clody.domain.alarm.dto.AlarmTotalInfo;
import com.clody.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmQueryService {

  private final AlarmRepository alarmRepository;

  public AlarmTotalInfo retrieveAlarmTotalInfo(Long userId){
    Alarm alarm = alarmRepository.findByUserId(userId);
    return AlarmTotalInfo.of(alarm);
  }
}
