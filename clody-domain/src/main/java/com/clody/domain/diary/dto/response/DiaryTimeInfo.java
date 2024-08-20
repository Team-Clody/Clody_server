package com.clody.domain.diary.dto.response;

import java.time.LocalDateTime;

public record DiaryTimeInfo(
    int HH,
    int MM,
    int SS
) {
    public static DiaryTimeInfo from(LocalDateTime createdAt) {
        return new DiaryTimeInfo(createdAt.getHour(), createdAt.getMinute(), createdAt.getSecond());
    }
}
