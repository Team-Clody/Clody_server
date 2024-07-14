package com.donkeys_today.server.presentation.Diary.dto.response;

import java.time.LocalDate;

public record DiaryTimeResponse(
        LocalDate createdDate,
        String HH,
        String MM,
        String SS

) {
    public static DiaryTimeResponse of(LocalDate createdDate, String hh, String mm, String ss) {

        return new DiaryTimeResponse(createdDate, hh, mm, ss);
    }

}
