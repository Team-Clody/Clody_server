package com.clody.clodyapi.diary.controller.dto.response;

import com.clody.domain.diary.ReplyStatus;

public record DiarySimpleInfo(
        int diaryCount,
        ReplyStatus replyStatus

) {
    public static DiarySimpleInfo of(int diaryCount, ReplyStatus replyStatus) {
        return new DiarySimpleInfo(diaryCount, replyStatus);
    }
}
