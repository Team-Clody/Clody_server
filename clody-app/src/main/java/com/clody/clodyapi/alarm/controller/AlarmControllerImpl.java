package com.clody.clodyapi.alarm.controller;

import com.clody.clodyapi.alarm.dto.request.AlarmUpdateRequest;
import com.clody.clodyapi.alarm.dto.response.AlarmFullResponse;
import com.clody.clodyapi.alarm.dto.response.AlarmResponse;
import com.clody.clodyapi.alarm.usecase.RetrieveAlarmInfoUsecase;
import com.clody.clodyapi.alarm.usecase.UpdateAlarmInfoUsecase;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AlarmControllerImpl implements AlarmSwagger{

  private final RetrieveAlarmInfoUsecase retrieveAlarmInfoUsecase;
  private final UpdateAlarmInfoUsecase updateAlarmInfoUsecase;

  @GetMapping("/alarm")
  public ResponseEntity<ApiResponse<AlarmResponse>> getUserAlarmInfo() {
    AlarmResponse response = retrieveAlarmInfoUsecase.retrieveAlarmInfo();
    return ResponseEntity.status(HttpStatus.OK).body(
        ApiResponse.success(SuccessType.OK_SUCCESS, response)
    );
  }

  @PostMapping("/alarm")
  public ResponseEntity<ApiResponse<AlarmFullResponse>> updateAlarmInfo(
      @RequestBody AlarmUpdateRequest  alarmUpdateRequest
  ){
    AlarmFullResponse response = updateAlarmInfoUsecase.updateAlarmInfo(alarmUpdateRequest);
    return ResponseEntity.status(HttpStatus.OK).body(
        ApiResponse.success(SuccessType.OK_SUCCESS, response)
    );
  }

}
