package com.donkeys_today.server.presentation.diary.dto.response;

import java.util.List;

public record DiaryListResponse(
        int totalMonthlyCount,
        List<DiaryFullInfo> diaries
) {
    public static DiaryListResponse of(int totalMonthlyCount, List<DiaryFullInfo> diaryInfo) {
        return new DiaryListResponse(totalMonthlyCount, diaryInfo);
    }
}

