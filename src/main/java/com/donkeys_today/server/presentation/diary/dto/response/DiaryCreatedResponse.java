package com.donkeys_today.server.presentation.diary.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record DiaryCreatedResponse(
    String createdAt,
    ReplyType replyType
) {
  public static DiaryCreatedResponse createDiaryWithStaticReply(LocalDateTime createdAt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return new DiaryCreatedResponse(createdAt.format(formatter), ReplyType.STATIC_REPLY);
  }

  public static DiaryCreatedResponse createDiaryWithDynamicReply(LocalDateTime createdAt){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return new DiaryCreatedResponse(createdAt.format(formatter),ReplyType.DYNAMIC_REPLY);
  }

  public static DiaryCreatedResponse createDiaryWithoutReply(LocalDateTime createdAt){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return new DiaryCreatedResponse(createdAt.format(formatter),ReplyType.NO_REPLY);
  }
}
