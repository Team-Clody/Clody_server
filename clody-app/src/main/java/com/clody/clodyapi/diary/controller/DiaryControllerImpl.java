package com.clody.clodyapi.diary.controller;

import com.clody.clodyapi.diary.controller.dto.request.DiaryRequest;
import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedResponse;
import com.clody.clodyapi.diary.controller.dto.response.DiaryCreatedTimeResponse;
import com.clody.clodyapi.diary.controller.dto.response.DiaryResponse;
import com.clody.clodyapi.diary.usecase.DiaryCreationUsecase;
import com.clody.clodyapi.diary.usecase.DiaryDeletionUsecase;
import com.clody.clodyapi.diary.usecase.DiaryQueryUsecase;
import com.clody.clodyapi.diary.usecase.DiaryRetrieverUsecase;
import com.clody.domain.diary.dto.response.DiaryListGetResponse;
import com.clody.domain.diary.dto.response.DiaryCalenderGetResponse;
import com.clody.support.constants.HeaderConstants;
import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.SuccessType;
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
public class DiaryControllerImpl  {

  private final DiaryCreationUsecase diaryCreationUsecase;
  private final DiaryDeletionUsecase diaryDeletionUsecase;
  private final DiaryRetrieverUsecase diaryRetrieverUsecase;
  private final DiaryQueryUsecase diaryQueryUsecase;


  @GetMapping("/calendar/list")
  public ResponseEntity<ApiResponse<DiaryListGetResponse>> getDiaryList(
      @RequestParam final int year,
      @RequestParam final int month) {
    final DiaryListGetResponse response = diaryRetrieverUsecase.retrieveListDiary(year, month);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @GetMapping("/calendar")
  public ResponseEntity<ApiResponse<DiaryCalenderGetResponse>> getDiaryCalender(
      @RequestParam final int year,
      @RequestParam final int month) {
    final DiaryCalenderGetResponse response = diaryRetrieverUsecase.retrieveCalendarDiary(year, month);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @GetMapping("/diary")
  public ResponseEntity<ApiResponse<DiaryResponse>> getDiary(final int year, final int month,
      final int date) {
    final DiaryResponse response = diaryQueryUsecase.getDiary(year, month, date);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  //  @Override
  @PostMapping("/diary")
  public ResponseEntity<ApiResponse<DiaryCreatedResponse>> postDiary(
      @RequestHeader(HeaderConstants.AUTHORIZATION) String accessToken,
      @RequestBody DiaryRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS,
            diaryCreationUsecase.createDiary(request)));
  }

  @GetMapping("/diary/time")
  public ResponseEntity<ApiResponse<DiaryCreatedTimeResponse>> getDiaryCreatedTime(
      @RequestParam final int year, @RequestParam final int month,
      @RequestParam final int date) {
    DateTimeValidator.validateLocalDateTime(year, month, date);
    final DiaryCreatedTimeResponse response = diaryQueryUsecase.getCreatedTime(year, month, date);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @DeleteMapping("/diary")
  public ResponseEntity<ApiResponse<?>> deleteDiary(
      int year, int month, int date) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.DELETED_SUCCESS,
            diaryDeletionUsecase.deleteDiary(year, month, date)));
  }
}
