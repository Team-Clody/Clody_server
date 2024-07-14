package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.presentation.reply.dto.response.ReplyContent;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ReplyController {

    @Operation(summary = "답변 조회 ", description = "QueryString 을 이용해 답변을 조회합니다..")
    @GetMapping("/reply")
    ResponseEntity<ApiResponse<ReplyContent>> getReply(
            @RequestParam @Parameter(name = "연도", description = "조회할 연도", required = true) final int year,
            @RequestParam @Parameter(name = "달", description = "조회할 달", required = true) final int month,
            @RequestParam @Parameter(name = "일", description = "조회할 일", required = true) final int day
    );
}
