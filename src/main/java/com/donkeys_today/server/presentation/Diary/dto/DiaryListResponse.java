package com.donkeys_today.server.presentation.Diary.dto;

import java.util.List;

public record DiaryListResponse(
        int totalMonthlyCount,
        List<DiaryInfo> diaries
) {
    public static DiaryListResponse of(int totalMonthlyCount, List<DiaryInfo> diaryInfo) {
        return new DiaryListResponse(totalMonthlyCount, diaryInfo);
    }
}

