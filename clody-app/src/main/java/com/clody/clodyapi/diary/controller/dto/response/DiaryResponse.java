package com.clody.clodyapi.diary.controller.dto.response;

import java.util.List;

public record DiaryResponse(
        List<DiaryContent> diaries
) {
    public static DiaryResponse of(List<DiaryContent> diaries) {
        return new DiaryResponse(diaries);
    }
}

