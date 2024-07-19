package com.donkeys_today.server.presentation.diary.dto.response;

import java.util.List;
import lombok.NoArgsConstructor;

public record DiaryListGetResponse(
        int totalCloverCount,
        List<DiaryFullInfo> diaries
) {
    public static DiaryListGetResponse of(int totalMonthlyCount, List<DiaryFullInfo> diaryFullInfos) {
        return new DiaryListGetResponse(totalMonthlyCount, diaryFullInfos);
    }
}

