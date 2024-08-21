package com.clody.clodyapi.diary.controller.dto.response;

import com.clody.domain.reply.UserReplyReadStatus;

public record DiarySimpleInfo(
        int diaryCount,
        UserReplyReadStatus userReplyReadStatus

) {
    public static DiarySimpleInfo of(int diaryCount, UserReplyReadStatus userReplyReadStatus) {
        return new DiarySimpleInfo(diaryCount, userReplyReadStatus);
    }
}
