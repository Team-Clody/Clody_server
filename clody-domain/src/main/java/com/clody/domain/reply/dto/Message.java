package com.clody.domain.reply.dto;

import com.clody.domain.reply.event.ReplyCreatedEvent;
import java.time.LocalDateTime;

public record Message(
    Long replyId,
    String content,
    LocalDateTime creationTime
) {
  public static Message of(Long replyId, String content ) {
    return new Message(replyId, content, LocalDateTime.now());
  }

  public static Message of(ReplyCreatedEvent event){
    return new Message(event.reply().getId(), event.parsedContent(), event.reply().getCreatedAt());
  }

}
