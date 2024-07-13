package com.donkeys_today.server.presentation.Diary.dto.response;

import java.time.LocalDate;

public record DiaryTimeResponse(
        LocalDate createdAt

) {
    public static DiaryTimeResponse of(LocalDate createdAt) {
        return new DiaryTimeResponse(createdAt);
    }

}
