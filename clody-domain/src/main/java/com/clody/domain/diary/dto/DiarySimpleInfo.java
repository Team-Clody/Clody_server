package com.clody.domain.diary.dto;

import com.clody.domain.reply.UserReplyReadStatus;

public record DiarySimpleInfo(
        int diaryCount,
        UserReplyReadStatus replyStatus,
        Boolean isDeleted


) {
    public static DiarySimpleInfo of(int diaryCount, UserReplyReadStatus replyStatus) {
        return new DiarySimpleInfo(diaryCount, replyStatus, false);
    }

    public static DiarySimpleInfo of(int diaryCount, UserReplyReadStatus replyStatus, Boolean isDeleted) {
        return new DiarySimpleInfo(diaryCount, replyStatus, isDeleted);
    }
}
