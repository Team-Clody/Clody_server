package com.clody.clodyapi.diary.controller.dto.response;

import java.util.List;

public record DiaryListGetResponse(
        int totalCloverCount,
        List<DiaryFullInfo> diaries
) {
    public static DiaryListGetResponse of(int totalMonthlyCount, List<DiaryFullInfo> diaryFullInfos) {
        return new DiaryListGetResponse(totalMonthlyCount, diaryFullInfos);
    }
}

