package com.clody.domain.diary.dto;

public record DiaryDeletionInfo(
    Long userId,
    int year,
    int month,
    int date
) {
    public static DiaryDeletionInfo of(Long userId, int year, int month, int date) {
        return new DiaryDeletionInfo(userId, year, month, date);
    }
}
