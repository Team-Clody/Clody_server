package com.clody.domain.reply.dto;

import com.clody.domain.reply.ReplyType;
import com.clody.domain.reply.event.ReplyCreatedEvent;
import java.time.LocalDateTime;

public record Message(
    Long replyId,
    String content,
    LocalDateTime creationTime,
    Integer version,
    ReplyType type
) {
  public static Message of(Long replyId, String content,Integer version, ReplyType type) {
    return new Message(replyId, content, LocalDateTime.now(), version,type);
  }

  public static Message of(ReplyCreatedEvent event){
    return new Message(event.reply().getId(), event.parsedContent(), event.reply().getCreatedAt(), event.version(),event.reply().getReplyType());
  }

}
