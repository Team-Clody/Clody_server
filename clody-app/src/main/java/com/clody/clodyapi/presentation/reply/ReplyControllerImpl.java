package com.clody.clodyapi.presentation.reply;

import com.clody.clodyapi.service.reply.ReplyService;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import com.clody.clodyapi.presentation.api.ReplyController;
import com.clody.clodyapi.presentation.reply.dto.response.ReplyResponse;
import com.clody.support.constants.HeaderConstants;
import com.clody.support.util.DateTimeValidator;
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
      @RequestHeader(HeaderConstants.AUTHORIZATION) final String accessToken,
      @RequestParam final int year,
      @RequestParam final int month,
      @RequestParam final int date) {
    DateTimeValidator.validateLocalDateTime(year, month, date);
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(
        SuccessType.READ_SUCCESS, replyService.readReply(year, month, date)));
  }
}
