package com.clody.domain.diary.dto;

import com.clody.domain.reply.UserReplyReadStatus;
import java.time.LocalDate;
import java.util.List;

public record DiaryFullInfo(
        int diaryCount,
        UserReplyReadStatus replyStatus,
        LocalDate date,
        List<DiaryContent> diary,

        Boolean isDeleted
) {
    public static DiaryFullInfo of(int diaryCount, UserReplyReadStatus replyStatus, LocalDate date, List<DiaryContent> diary) {
        return new DiaryFullInfo(diaryCount, replyStatus, date, diary, false);
    }


    public static DiaryFullInfo of(int diaryCount, UserReplyReadStatus replyStatus, LocalDate date, List<DiaryContent> diary, Boolean isDeleted) {
        return new DiaryFullInfo(diaryCount, replyStatus, date, diary, isDeleted);
    }
}
