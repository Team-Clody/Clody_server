package com.clody.clodyapi.alarm.service;

import com.clody.clodyapi.alarm.dto.response.AlarmResponse;
import com.clody.clodyapi.alarm.usecase.RetrieveAlarmInfoUsecase;
import com.clody.domain.alarm.dto.AlarmTotalInfo;
import com.clody.domain.alarm.service.AlarmQueryService;
import com.clody.support.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmRetrieveService implements RetrieveAlarmInfoUsecase {

  private final AlarmQueryService alarmQueryService;

  public AlarmResponse retrieveAlarmInfo(){
    Long currentUserId = JwtUtil.getLoginMemberId();
    AlarmTotalInfo totalInfo = alarmQueryService.retrieveAlarmTotalInfo(currentUserId);
    return AlarmResponse.of(totalInfo);
  }
}
