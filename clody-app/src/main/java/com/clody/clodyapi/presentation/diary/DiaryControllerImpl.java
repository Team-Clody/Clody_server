package com.clody.clodyapi.presentation.diary;

import com.clody.clodyapi.presentation.diary.dto.response.DiaryCalenderGetResponse;
import com.clody.clodyapi.service.diary.DiaryService;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
import com.clody.clodyapi.presentation.api.DiaryController;
import com.clody.clodyapi.presentation.diary.dto.request.DiaryRequest;
import com.clody.clodyapi.presentation.diary.dto.response.DiaryCreatedResponse;
import com.clody.clodyapi.presentation.diary.dto.response.DiaryListGetResponse;
import com.clody.clodyapi.presentation.diary.dto.response.DiaryResponse;
import com.clody.clodyapi.presentation.user.dto.response.DiaryCreatedTimeGetResponse;
import com.clody.support.constants.HeaderConstants;
import com.clody.support.util.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DiaryControllerImpl implements DiaryController {

  private final DiaryService diaryService;

  @GetMapping("/calendar/list")
  @Override
  public ResponseEntity<ApiResponse<DiaryListGetResponse>> getDiaryList(
      @RequestParam final int year,
      @RequestParam final int month) {
    DateTimeValidator.validateLocalDate(year, month);
    final DiaryListGetResponse response = diaryService.getDiaryList(year, month);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @GetMapping("/calendar")
  @Override
  public ResponseEntity<ApiResponse<DiaryCalenderGetResponse>> getDiaryCalender(
      @RequestParam final int year,
      @RequestParam final int month) {
    DateTimeValidator.validateLocalDate(year, month);
    final DiaryCalenderGetResponse response = diaryService.getDiaryCalender(year, month);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @GetMapping("/diary")
  @Override
  public ResponseEntity<ApiResponse<DiaryResponse>> getDiary(int year, int month, int date) {
    DateTimeValidator.validateLocalDateTime(year, month, date);
    final DiaryResponse response = diaryService.getDiary(year, month, date);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @Override
  @PostMapping("/diary")
  public ResponseEntity<ApiResponse<DiaryCreatedResponse>> postDiary(
      @RequestHeader(HeaderConstants.AUTHORIZATION) String accessToken,
      @RequestBody DiaryRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, diaryService.createDiary(request)));
  }

  @Override
  @GetMapping("/diary/time")
  public ResponseEntity<ApiResponse<DiaryCreatedTimeGetResponse>> getDiaryCreatedTime(
      @RequestParam final int year, @RequestParam final int month,
      @RequestParam final int date) {
    DateTimeValidator.validateLocalDateTime(year, month, date);
    final DiaryCreatedTimeGetResponse response = diaryService.getDiaryCreatedTime(year, month, date);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @Override
  @DeleteMapping("/diary")
  public ResponseEntity<ApiResponse<?>> deleteDiary(
      int year, int month, int date) {
    DateTimeValidator.validateLocalDateTime(year, month, date);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.DELETED_SUCCESS,diaryService.deleteDiary(year, month, date)));
  }
}
