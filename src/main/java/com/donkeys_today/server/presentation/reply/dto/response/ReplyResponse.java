package com.donkeys_today.server.presentation.reply.dto.response;

public record ReplyResponse(
    String content
) {
  public static ReplyResponse of(String content) {
    return new ReplyResponse(content);
  }
}
