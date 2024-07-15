package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.reply.dto.response.ReplyResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "답변 관련")
@RequestMapping("/api/v1")
@RestController
public interface ReplyController {

  @GetMapping("/reply")
  ResponseEntity<ApiResponse<ReplyResponse>> getReply(
      @RequestHeader(Constants.AUTHORIZATION) final String accessToken,
      @RequestParam @Parameter(name = "연도", description = "조회할 연도", required = true) final String year,
      @RequestParam @Parameter(name = "달", description = "조회할 달", required = true) final String month,
      @RequestParam @Parameter(name = "일", description = "조회할 일", required = true) final String date);
}
