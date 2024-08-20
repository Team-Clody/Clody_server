package com.clody.domain.alarm.service;

import com.clody.domain.alarm.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmService {

  private final AlarmRepository alarmRepository;

}
