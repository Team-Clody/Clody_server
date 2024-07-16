package com.donkeys_today.server.presentation.diary;

import com.donkeys_today.server.application.diary.DiaryService;
import com.donkeys_today.server.common.constants.Constants;
import com.donkeys_today.server.presentation.api.DiaryController;
import com.donkeys_today.server.presentation.diary.dto.request.DiaryRequest;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCalenderGetResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCreatedResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryListGetResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryResponse;
import com.donkeys_today.server.presentation.user.dto.response.DiaryCreatedTimeGetResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
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

    @GetMapping("/calender/list")
    @Override
    public ResponseEntity<ApiResponse<DiaryListGetResponse>> getDiaryList(@RequestParam final int year,
                                                                          @RequestParam final int month) {
        final DiaryListGetResponse response = diaryService.getDiaryList(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

    @GetMapping("/calender")
    @Override
    public ResponseEntity<ApiResponse<DiaryCalenderGetResponse>> getDiaryCalender(@RequestParam final int year,
                                                                                  @RequestParam final int month) {

        final DiaryCalenderGetResponse response = diaryService.getDiaryCalender(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

  @GetMapping("/diary")
  @Override
  public ResponseEntity<ApiResponse<DiaryResponse>> getDiary(int year, int month, int day) {

    final DiaryResponse response = diaryService.getDiary(year, month, day);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
  }

  @PostMapping("/diary")
  public ResponseEntity<ApiResponse<DiaryCreatedResponse>> postDiary(
      @RequestHeader(Constants.AUTHORIZATION) String accessToken,
      @RequestBody DiaryRequest request) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.CREATED_SUCCESS, diaryService.createDiary(request)));
  }

    @Override
    @GetMapping("/dairy/time")
    public ResponseEntity<ApiResponse<DiaryCreatedTimeGetResponse>> getDiaryCreatedTime(@RequestParam final int year, @RequestParam final int month,
                                                                                        @RequestParam final int day) {
        final DiaryCreatedTimeGetResponse response = diaryService.getDiaryCreatedTime(year, month, day);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

  @Override
  @DeleteMapping("/diary")
  public ResponseEntity<ApiResponse<?>> deleteDiary(
      int year,
      int month,
      int date) {
    diaryService.deleteDiary(year, month, date);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success(SuccessType.DELETED_SUCCESS));
  }
}
