package com.donkeys_today.server.presentation.diary;

import com.donkeys_today.server.application.diary.DiaryService;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryCalenderResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryListResponse;
import com.donkeys_today.server.presentation.diary.dto.response.DiaryResponse;
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

    @GetMapping("/diary")
    @Override
    public ResponseEntity<ApiResponse<DiaryResponse>> getDiary(int year, int month, int day) {

        final DiaryResponse response = diaryService.getDiary(year, month, day);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(SuccessType.OK_SUCCESS, response));
    }

}
