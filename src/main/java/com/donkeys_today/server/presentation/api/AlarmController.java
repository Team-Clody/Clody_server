package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.alarm.dto.request.AlarmRequest;
import com.donkeys_today.server.presentation.alarm.dto.response.AlarmResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "알림 관련", description = "알림 설정과 관련된 기능을 수행하는 API 입니다.")
@RequestMapping("/api/v1")
@RestController
public interface AlarmController {

  @Operation(summary = "알림 설정 조회", description = "현재 유저의 알림 설정 현황을 조회합니다.")
  @GetMapping("/alarm")
  ResponseEntity<ApiResponse<AlarmResponse>> getAlarmInfo(
      @RequestHeader(Constants.AUTHORIZATION) @Parameter(name = "Authorization", description = "클라이언트의 access Token", required = true) final String accessToken
  );

  @Operation(summary = "알림 설정 변경", description = "RequestBody를 통해 알림 설정을 변경합니다.")
  @PostMapping("/alarm")
  ResponseEntity<ApiResponse<AlarmResponse>> postAlarmInfo(
      @RequestHeader(Constants.AUTHORIZATION) @Parameter(name = "Authorization", description = "클라이언트의 access Token", required = true) final String accessToken,
      @RequestBody(
          description = "알림 설정을 위해 부가적으로 받는 정보 객체",
          required = true,
          content = @Content(schema = @Schema(implementation = AlarmRequest.class))
      ) final AlarmRequest request
  );
}
