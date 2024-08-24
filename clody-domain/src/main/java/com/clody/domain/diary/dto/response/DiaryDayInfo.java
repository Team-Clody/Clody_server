package com.clody.domain.diary.dto.response;

import com.clody.domain.diary.dto.DiaryContent;
import com.clody.domain.reply.UserReplyReadStatus;
import java.time.LocalDate;
import java.util.List;

public record DiaryDayInfo(
        int diaryCount,
        UserReplyReadStatus replyStatus,
        LocalDate date,
        List<DiaryContent> diary,

        Boolean isDeleted
) {
    public static DiaryDayInfo of(int diaryCount, UserReplyReadStatus replyStatus, LocalDate date, List<DiaryContent> diary) {
        return new DiaryDayInfo(diaryCount, replyStatus, date, diary, false);
    }


    public static DiaryDayInfo of(int diaryCount, UserReplyReadStatus replyStatus, LocalDate date, List<DiaryContent> diary, Boolean isDeleted) {
        return new DiaryDayInfo(diaryCount, replyStatus, date, diary, isDeleted);
    }
}
