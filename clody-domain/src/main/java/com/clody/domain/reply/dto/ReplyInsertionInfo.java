package com.clody.domain.reply.dto;

public record ReplyInsertionInfo(
    Long replyId,
    String content,
    Integer version
) {

    public static ReplyInsertionInfo of(Long replyId, String content, Integer version) {
        return new ReplyInsertionInfo(replyId, content, version);
    }

    public static ReplyInsertionInfo of(Message message) {
        return new ReplyInsertionInfo(message.replyId(), message.content(), message.version());
    }
}
