package com.donkeys_today.server.presentation.Diary.dto;

import java.util.List;

public record CalenderListResponse(
        int monthlyCloverCount,
        List<DiaryInfo> diaries
) {
    public static CalenderListResponse of(int monthlyCloverCount, List<DiaryInfo> diaryInfo) {
        return new CalenderListResponse(monthlyCloverCount, diaryInfo);
    }
}

