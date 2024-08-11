package com.clody.domain.diary.dto;

import com.clody.domain.diary.ReplyStatus;

public record DiarySimpleInfo(
        int diaryCount,
        ReplyStatus replyStatus,
        Boolean isDeleted


) {
    public static DiarySimpleInfo of(int diaryCount, ReplyStatus replyStatus) {
        return new DiarySimpleInfo(diaryCount, replyStatus, false);
    }

    public static DiarySimpleInfo of(int diaryCount, ReplyStatus replyStatus, Boolean isDeleted) {
        return new DiarySimpleInfo(diaryCount, replyStatus, isDeleted);
    }
}
