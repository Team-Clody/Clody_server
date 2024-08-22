package com.clody.clodyapi.alarm.controller;

import com.clody.clodyapi.alarm.dto.request.AlarmUpdateRequest;
import com.clody.clodyapi.alarm.dto.response.AlarmFullResponse;
import com.clody.clodyapi.alarm.dto.response.AlarmResponse;
import com.clody.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알람", description = "알람과 관련하여 RU를 담당하는 API 입니다.")
@RequestMapping("/api/v1")
@RestController
public interface AlarmSwagger {

  @Operation(summary = "사용자 알람 설정 조회", description = "사용자의 알람 설정 현황을 조회합니다.")
  @GetMapping("/alarm")
  ResponseEntity<ApiResponse<AlarmResponse>> getUserAlarmInfo();

  @Operation(summary = "사용자 알람 설정 변경 ", description = "RequestBody를 이용해 사용자의 알람 설정을 변경합니다.")
  @PostMapping("/alarm")
  ResponseEntity<ApiResponse<AlarmFullResponse>> updateAlarmInfo(
      @RequestBody @Parameter(name = "알람 변경 정보", description = "각 요소는 Nullable 입니다.", required = true)
      AlarmUpdateRequest request
  );
}
