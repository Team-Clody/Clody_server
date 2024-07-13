package com.donkeys_today.server.presentation.Diary.dto.response;

import java.util.List;

public record DiaryCalenderResponse(
        int totalMonthlyCount,
        List<DiarySimpleInfo> diaries
) {
    public static DiaryCalenderResponse of(int totalMonthlyCount, List<DiarySimpleInfo> diaryInfo) {
        return new DiaryCalenderResponse(totalMonthlyCount, diaryInfo);
    }
}

