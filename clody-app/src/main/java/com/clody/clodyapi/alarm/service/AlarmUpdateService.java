package com.clody.clodyapi.alarm.service;

import com.clody.clodyapi.alarm.dto.request.AlarmUpdateRequest;
import com.clody.clodyapi.alarm.dto.response.AlarmFullResponse;
import com.clody.clodyapi.alarm.usecase.UpdateAlarmInfoUsecase;
import com.clody.domain.alarm.dto.AlarmTotalInfo;
import com.clody.domain.alarm.dto.UpdateAlarmCommand;
import com.clody.domain.alarm.service.AlarmCommandService;
import com.clody.support.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmUpdateService implements UpdateAlarmInfoUsecase {

  private final AlarmCommandService alarmCommandService;

  @Override
  public AlarmFullResponse updateAlarmInfo(AlarmUpdateRequest request) {

    UpdateAlarmCommand command = AlarmUpdateRequest.toUpdateAlarmCommand(request);
    Long userId = JwtUtil.getLoginMemberId();
    AlarmTotalInfo info = alarmCommandService.updateAlarm(userId, command);
    return AlarmFullResponse.parseFromAlarmInfo(info);
  }
}
