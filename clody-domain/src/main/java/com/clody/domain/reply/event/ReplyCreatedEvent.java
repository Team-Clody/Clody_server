package com.clody.domain.reply.event;

import com.clody.domain.reply.Reply;

public record ReplyCreatedEvent(
    Reply reply,
    Long userId,
    String parsedContent,
    Integer version
) {
}
