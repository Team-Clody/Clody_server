package com.clody.clodyapi.diary.controller.dto.response;

import com.clody.domain.reply.ReplyType;

public record DiaryCreatedTimeResponse(
    int HH,
    int mm,
    int ss,
    ReplyType replyType
) {
    public static DiaryCreatedTimeResponse of(int HH, int mm, int ss, ReplyType type) {
        return new DiaryCreatedTimeResponse(HH, mm, ss, type);
    }
}
