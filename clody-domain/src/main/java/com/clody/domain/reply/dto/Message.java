package com.clody.domain.reply.dto;

import com.clody.domain.reply.ReplyType;
import com.clody.domain.reply.event.ReplyCreatedEvent;
import java.time.LocalDateTime;

public record Message(
    Long replyId,
    Long userId,
    String content,
    LocalDateTime creationTime,
    Integer version,
    ReplyType type
) {
  public static Message of(Long replyId, Long userId, String content,Integer version, ReplyType type) {
    return new Message(replyId, userId, content, LocalDateTime.now(), version,type);
  }

  public static Message of(ReplyCreatedEvent event){
    return new Message(event.reply().getId(),event.userId(), event.parsedContent(), event.reply().getCreatedAt(), event.version(),event.reply().getReplyType());
  }

}
