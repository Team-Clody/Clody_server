package com.clody.domain.diary.dto;

import com.clody.domain.diary.dto.response.DiaryDayInfo;
import java.util.List;

public record DiaryListGetResponse(
        int totalCloverCount,
        List<DiaryDayInfo> diaries
) {
    public static DiaryListGetResponse of(int totalMonthlyCount, List<DiaryDayInfo> diaryFullInfos) {
        return new DiaryListGetResponse(totalMonthlyCount, diaryFullInfos);
    }
}

