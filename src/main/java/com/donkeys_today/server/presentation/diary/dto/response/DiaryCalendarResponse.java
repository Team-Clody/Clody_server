package com.donkeys_today.server.presentation.diary.dto.response;

import java.util.List;

public record DiaryCalendarResponse(
        int totalMonthlyCount,
        List<DiarySimpleInfo> diaries
) {
    public static DiaryCalendarResponse of(int totalMonthlyCount, List<DiarySimpleInfo> diaryInfo) {
        return new DiaryCalendarResponse(totalMonthlyCount, diaryInfo);
    }
}

