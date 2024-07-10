package com.donkeys_today.server.presentation.Diary;

import com.donkeys_today.server.application.diary.DiaryService;
import com.donkeys_today.server.presentation.Diary.dto.DiaryCalenderResponse;
import com.donkeys_today.server.presentation.Diary.dto.DiaryListResponse;
import com.donkeys_today.server.presentation.api.DiaryController;
import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.SuccessType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<ApiResponse<DiaryListResponse>> getDiaryList(@RequestParam final int year,
                                                                       @RequestParam final int month) {
        final DiaryListResponse response = diaryService.getDiaryList(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

    @GetMapping("/calender")
    @Override
    public ResponseEntity<ApiResponse<DiaryCalenderResponse>> getDiaryCalender(@RequestParam final int year,
                                                                               @RequestParam final int month) {
        final DiaryCalenderResponse response = diaryService.getDiaryCalender(year, month);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }
}