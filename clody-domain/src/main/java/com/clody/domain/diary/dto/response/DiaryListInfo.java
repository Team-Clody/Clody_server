package com.clody.domain.diary.dto.response;

import java.util.List;

public record DiaryListInfo(
        int totalCloverCount,
        List<DiaryDayInfo> diaries
) {
    public static DiaryListInfo of(int totalMonthlyCount, List<DiaryDayInfo> diaryDayInfos) {
        return new DiaryListInfo(totalMonthlyCount, diaryDayInfos);
    }
}

