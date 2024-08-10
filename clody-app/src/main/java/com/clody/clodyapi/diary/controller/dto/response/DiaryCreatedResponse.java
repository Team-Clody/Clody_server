package com.clody.clodyapi.diary.controller.dto.response;

import com.clody.domain.reply.ReplyType;
import java.time.LocalDateTime;

public record DiaryCreatedResponse(
    LocalDateTime createdAt,
    ReplyType replyType
) {
//  public static DiaryCreatedResponse createDiaryWithStaticReply(LocalDateTime createdAt) {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    return new DiaryCreatedResponse(createdAt.format(formatter), ReplyType.STATIC_REPLY);
//  }
//
//  public static DiaryCreatedResponse createDiaryWithDynamicReply(LocalDateTime createdAt){
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    return new DiaryCreatedResponse(createdAt.format(formatter),ReplyType.DYNAMIC_REPLY);
//  }
//
//  public static DiaryCreatedResponse createDiaryWithoutReply(LocalDateTime createdAt){
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    return new DiaryCreatedResponse(createdAt.format(formatter),ReplyType.NO_REPLY);
//  }

  public static DiaryCreatedResponse of(LocalDateTime createdAt, ReplyType replyType) {
    return new DiaryCreatedResponse(createdAt, replyType);
  }
}
