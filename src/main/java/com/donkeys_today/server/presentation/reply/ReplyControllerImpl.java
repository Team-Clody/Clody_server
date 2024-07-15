package com.donkeys_today.server.presentation.reply;

import com.donkeys_today.server.application.reply.ReplyService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.api.ReplyController;
import com.donkeys_today.server.presentation.reply.dto.response.ReplyResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReplyControllerImpl implements ReplyController {

  private final ReplyService replyService;

  @GetMapping("/reply")
  public ResponseEntity<ApiResponse<ReplyResponse>> getReply(
      @RequestHeader(Constants.AUTHORIZATION) final String accessToken,
      @RequestParam final String year,
      @RequestParam final String month,
      @RequestParam final String date) {
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        SuccessType.READ_SUCCESS, replyService.readReply(year, month, date)));
  }
}
