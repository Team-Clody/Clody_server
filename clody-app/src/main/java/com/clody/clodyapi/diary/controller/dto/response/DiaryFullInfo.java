package com.clody.clodyapi.diary.controller.dto.response;

import com.clody.domain.reply.UserReplyReadStatus;
import java.time.LocalDate;
import java.util.List;

public record DiaryFullInfo(
        int diaryCount,
        UserReplyReadStatus userReplyReadStatus,
        LocalDate date,
        List<DiaryContent> diary
) {
    public static DiaryFullInfo of(int diaryCount, UserReplyReadStatus userReplyReadStatus, LocalDate date, List<DiaryContent> diary) {
        return new DiaryFullInfo(diaryCount, userReplyReadStatus, date, diary);
    }
}
