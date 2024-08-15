package com.clody.domain.reply.dto;

import java.time.LocalDateTime;

public record DequeuedMessage(
    Long replyId,
    String content,
    LocalDateTime creationTime
) {
  public static DequeuedMessage of(Long replyId, String content ) {
    return new DequeuedMessage(replyId, content, LocalDateTime.now());
  }
}
