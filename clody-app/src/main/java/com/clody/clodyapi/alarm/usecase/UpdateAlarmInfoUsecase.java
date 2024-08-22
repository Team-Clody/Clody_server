package com.clody.clodyapi.alarm.usecase;

import com.clody.clodyapi.alarm.dto.request.AlarmUpdateRequest;
import com.clody.clodyapi.alarm.dto.response.AlarmFullResponse;

public interface UpdateAlarmInfoUsecase {

  AlarmFullResponse updateAlarmInfo(AlarmUpdateRequest request);

}
