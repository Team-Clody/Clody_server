package com.donkeys_today.server.presentation.Diary.dto;


import com.donkeys_today.server.domain.diary.ReplyStatus;
import java.time.LocalDate;
import java.util.List;

public record DiaryInfo(
        int diaryCount,
        ReplyStatus replyStatus,

        LocalDate date,

        List<DiaryContent> diary
) {
    public static DiaryInfo of(int diaryCount, ReplyStatus replyStatus, LocalDate date, List<DiaryContent> diary) {
        return new DiaryInfo(diaryCount, replyStatus, date, diary);
    }
}