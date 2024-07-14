package com.donkeys_today.server.presentation.reply.dto.response;

public record ReplyContent(
        String content
) {
    public static ReplyContent of(String content) {
        return new ReplyContent(content);
    }
}
