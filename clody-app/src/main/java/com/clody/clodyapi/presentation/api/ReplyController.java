package com.clody.clodyapi.presentation.api;

import com.clody.clodyapi.presentation.reply.dto.response.ReplyResponse;
import com.clody.support.dto.ApiResponse;
import com.clody.support.constants.HeaderConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "답변", description = "답변과 관련된 기능을 수행하는 API 입니다.")
@RequestMapping("/api/v1")
@RestController
public interface ReplyController {

  @GetMapping("/reply")
  @Operation(summary = "답변 조회 ", description = "액세스 토큰과 년/월/일을 통해 답변을 조회합니다.")
  ResponseEntity<ApiResponse<ReplyResponse>> getReply(
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String accessToken,
      @RequestParam @Parameter(name = "연도", description = "조회할 연도", required = true) final int year,
      @RequestParam @Parameter(name = "달", description = "조회할 달", required = true) final int month,
      @RequestParam @Parameter(name = "일", description = "조회할 일", required = true) final int date);
}
