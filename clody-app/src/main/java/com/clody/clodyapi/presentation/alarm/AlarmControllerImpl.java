package com.clody.clodyapi.presentation.alarm;

import com.clody.clodyapi.service.alarm.AlarmService;
import com.clody.clodyapi.service.user.UserService;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import com.clody.clodyapi.presentation.alarm.dto.request.AlarmRequest;
import com.clody.clodyapi.presentation.alarm.dto.response.AlarmResponse;
import com.clody.clodyapi.presentation.api.AlarmController;
import com.clody.support.constants.HeaderConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AlarmControllerImpl implements AlarmController {

  private final AlarmService alarmService;
  private final UserService userService;

  @Override
  @GetMapping("/alarm")
  public ResponseEntity<ApiResponse<AlarmResponse>> getAlarmInfo(
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String accessToken) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.READ_SUCCESS, alarmService.getUserAlarm()));
  }

  @PostMapping("/alarm")
  public ResponseEntity<ApiResponse<AlarmResponse>> postAlarmInfo(
      @RequestHeader(HeaderConstants.AUTHORIZATION) String accessToken,
      @RequestBody final AlarmRequest alarmRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS,
            alarmService.updateAlarm(alarmRequest)));
  }
}
