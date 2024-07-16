package com.donkeys_today.server.presentation.diary.dto.response;

import java.util.List;

public record DiaryListGetResponse(
        int totalMonthlyCount,
        List<DiaryFullInfo> diaries
) {
    public static DiaryListGetResponse of(int totalMonthlyCount, List<DiaryFullInfo> diaryFullInfos) {
        return new DiaryListGetResponse(totalMonthlyCount, diaryFullInfos);
    }
}

