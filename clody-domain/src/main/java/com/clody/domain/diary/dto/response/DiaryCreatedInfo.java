package com.clody.domain.diary.dto.response;

import java.time.LocalDateTime;

public record DiaryCreatedInfo(
    int HH,
    int MM,
    int SS,
    boolean isFirst
) {
    public static DiaryCreatedInfo from(LocalDateTime createdAt, boolean isFirst) {
        return new DiaryCreatedInfo(createdAt.getHour(), createdAt.getMinute(), createdAt.getSecond(), isFirst);
    }
}
