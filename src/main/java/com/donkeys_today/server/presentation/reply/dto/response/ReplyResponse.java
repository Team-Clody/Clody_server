package com.donkeys_today.server.presentation.reply.dto.response;

public record ReplyResponse(
    String nickname,
    String content,
    int month,
    int date
) {
  public static ReplyResponse of(String nickName, String content, int month, int date){
    return new ReplyResponse(nickName, content, month, date);
  }
}
