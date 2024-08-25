package com.clody.domain.diary.dto.response;

import com.clody.domain.diary.dto.DiarySimpleInfo;
import java.util.List;

public record DiaryCalenderGetResponse(
        int totalCloverCount,
        List<DiaryDayInfo> diaries
) {
    public static DiaryCalenderGetResponse of(int totalMonthlyCount, List<DiaryDayInfo> diaryInfo) {
        return new DiaryCalenderGetResponse(totalMonthlyCount, diaryInfo);
    }
}

