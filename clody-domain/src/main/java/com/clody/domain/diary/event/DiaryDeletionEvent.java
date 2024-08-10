package com.clody.domain.diary.event;

import java.time.LocalDate;

public record DiaryDeletionEvent(
    Long userId,
    LocalDate diaryCreationDate

) {
    public static DiaryDeletionEvent of(Long userId, LocalDate diaryCreationDate) {
        return new DiaryDeletionEvent(userId, diaryCreationDate);
    }
}
