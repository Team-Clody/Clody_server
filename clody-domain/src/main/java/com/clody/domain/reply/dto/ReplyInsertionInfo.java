package com.clody.domain.reply.dto;

public record ReplyInsertionInfo(
    Long replyId,
    String content
) {

    public static ReplyInsertionInfo of(Long replyId, String content) {
        return new ReplyInsertionInfo(replyId, content);
    }
}
