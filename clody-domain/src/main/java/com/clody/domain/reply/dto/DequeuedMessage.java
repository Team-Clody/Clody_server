package com.clody.domain.reply.dto;

import com.clody.domain.reply.ReplyType;
import java.time.LocalDateTime;

public record DequeuedMessage(
    Long replyId,
    String content,
    LocalDateTime creationTime,
    Integer version,
    ReplyType type
) {

  public static DequeuedMessage of(Long replyId, String content, Integer version,
      ReplyType type) {
    return new DequeuedMessage(replyId, content, LocalDateTime.now(), version, type);
  }
}
