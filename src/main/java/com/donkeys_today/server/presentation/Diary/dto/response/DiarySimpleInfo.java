package com.donkeys_today.server.presentation.Diary.dto.response;


import com.donkeys_today.server.domain.diary.ReplyStatus;

public record DiarySimpleInfo(
        int diaryCount,
        ReplyStatus replyStatus

) {
    public static DiarySimpleInfo of(int diaryCount, ReplyStatus replyStatus) {
        return new DiarySimpleInfo(diaryCount, replyStatus);
    }
}