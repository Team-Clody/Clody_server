package com.donkeys_today.server.presentation.reply.dto.response;

public record ReplyResponse(
    String nickname,
    String content
) {
  public static ReplyResponse of(String nickname, String content) {
    return new ReplyResponse(nickname, content);
  }
}
