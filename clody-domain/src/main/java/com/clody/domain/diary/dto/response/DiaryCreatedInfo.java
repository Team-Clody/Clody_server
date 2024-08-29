package com.clody.domain.diary.dto.response;

import com.clody.domain.reply.ReplyType;
import java.time.LocalDateTime;

public record DiaryCreatedInfo(
    int HH,
    int MM,
    int SS,
    ReplyType replyType
) {
    public static DiaryCreatedInfo from(LocalDateTime createdAt, ReplyType replyType) {
        return new DiaryCreatedInfo(createdAt.getHour(), createdAt.getMinute(), createdAt.getSecond(), replyType);
    }
}
