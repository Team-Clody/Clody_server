package com.donkeys_today.server.presentation.api;

import com.donkeys_today.server.presentation.Diary.dto.response.DiaryCalenderResponse;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryListResponse;
import com.donkeys_today.server.presentation.Diary.dto.response.DiaryResponse;
import com.donkeys_today.server.support.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "일기/조회/생성/삭제")
@RequestMapping("/api/v1")
@RestController
public interface DiaryController {

    @Operation(summary = "리스트뷰 일기 조회 ", description = "QueryString 을 이용해 일기(리스트뷰)조회를 합니다.")
    @GetMapping("/calender/list")
    ResponseEntity<ApiResponse<DiaryListResponse>> getDiaryList(
            @RequestParam @Parameter(name = "연도", description = "조회할 연도", required = true) final int year,
            @RequestParam @Parameter(name = "달", description = "조회할 달", required = true) final int month

    );

    @Operation(summary = "캘린더뷰 일기 조회 ", description = "QueryString 을 이용해 일기(캘린더뷰)조회를 합니다.")
    @GetMapping("/calender")
    ResponseEntity<ApiResponse<DiaryCalenderResponse>> getDiaryCalender(
            @RequestParam @Parameter(name = "연도", description = "조회할 연도", required = true) final int year,
            @RequestParam @Parameter(name = "달", description = "조회할 달", required = true) final int month

    );

    @Operation(summary = "일단위 일기 조회 ", description = "QueryString 을 이용해 일단위 일기 조회를 합니다..")
    @GetMapping("/dairy")
    ResponseEntity<ApiResponse<DiaryResponse>> getDiary(
            @RequestParam @Parameter(name = "연도", description = "조회할 연도", required = true) final int year,
            @RequestParam @Parameter(name = "달", description = "조회할 달", required = true) final int month,
            @RequestParam @Parameter(name = "일", description = "조회할 일", required = true) final int day

    );


}
