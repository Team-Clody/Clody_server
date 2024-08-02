package com.clody.clodyapi.presentation.diary.dto.response;

import java.util.List;

public record DiaryCalenderGetResponse(
        int totalCloverCount,
        List<DiarySimpleInfo> diaries
) {
    public static DiaryCalenderGetResponse of(int totalMonthlyCount, List<DiarySimpleInfo> diaryInfo) {
        return new DiaryCalenderGetResponse(totalMonthlyCount, diaryInfo);
    }
}

