package com.clody.domain.reply.dto;

import com.clody.domain.reply.ReplyType;
import java.time.LocalDateTime;

public record CreationMessage(
    Long replyId,
    Long userId,
    String content,
    LocalDateTime creationTime,
    Integer version,
    ReplyType type
) {

  public static CreationMessage from(Message message){
    return new CreationMessage(message.replyId(), message.userId(), message.content(), message.creationTime(),
        message.version(), message.type());
  }

}
