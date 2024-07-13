package com.donkeys_today.server.presentation.diary;

import com.donkeys_today.server.application.diary.DiaryService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DiaryController {

  private final DiaryService diaryService;

  @PostMapping("/diary")
  public ResponseEntity<ApiResponse<?>> createDiary(@RequestHeader(Constants.AUTHORIZATION) String accessToken, @RequestBody DiaryRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, diaryService.createDiary(request)));
  }

}
