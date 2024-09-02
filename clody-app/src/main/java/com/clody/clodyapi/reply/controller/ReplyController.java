package com.clody.clodyapi.reply.controller;

import com.clody.clodyapi.reply.usecase.ReplyRetrieveUsecase;
import com.clody.domain.reply.dto.ReplyResponse;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class ReplyController implements ReplyApi {

  private final ReplyRetrieveUsecase replyRetrieveUsecase;

  @Override
  public ResponseEntity<ApiResponse<ReplyResponse>> getReply(String accessToken,
      int year, int month, int date) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(
            SuccessType.OK_SUCCESS,
        replyRetrieveUsecase.getReplyByDate(year, month, date)
        ));
  }
}
