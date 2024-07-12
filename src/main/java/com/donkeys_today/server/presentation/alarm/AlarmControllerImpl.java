package com.donkeys_today.server.presentation.alarm;

import com.donkeys_today.server.application.alarm.AlarmService;
import com.donkeys_today.server.application.user.UserService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.alarm.dto.request.AlarmRequest;
import com.donkeys_today.server.presentation.alarm.dto.response.AlarmResponse;
import com.donkeys_today.server.presentation.api.AlarmController;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
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
      @RequestHeader(Constants.AUTHORIZATION) final String accessToken) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.READ_SUCCESS, alarmService.getUserAlarm()));
  }

  @PostMapping("/alarm")
  public ResponseEntity<ApiResponse<AlarmResponse>> postAlarmInfo(
      @RequestHeader(Constants.AUTHORIZATION) String accessToken,
      @RequestBody final AlarmRequest alarmRequest) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS,
            alarmService.updateAlarm(alarmRequest)));
  }
}
