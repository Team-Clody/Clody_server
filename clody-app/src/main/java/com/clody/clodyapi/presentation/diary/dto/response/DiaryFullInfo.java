package com.clody.clodyapi.presentation.diary.dto.response;

import com.clody.domain.diary.ReplyStatus;
import java.time.LocalDate;
import java.util.List;

public record DiaryFullInfo(
        int diaryCount,
        ReplyStatus replyStatus,
        LocalDate date,
        List<DiaryContent> diary
) {
    public static DiaryFullInfo of(int diaryCount, ReplyStatus replyStatus, LocalDate date, List<DiaryContent> diary) {
        return new DiaryFullInfo(diaryCount, replyStatus, date, diary);
    }
}
