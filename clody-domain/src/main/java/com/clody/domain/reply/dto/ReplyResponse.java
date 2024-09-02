package com.clody.domain.reply.dto;

import com.clody.domain.reply.Reply;

public record ReplyResponse(
    String nickname,
    String content,
    int month,
    int date
) {

  public static ReplyResponse of(String nickName, String content, int month, int date) {
    return new ReplyResponse(nickName, content, month, date);
  }

  public static ReplyResponse from(Reply reply) {
    return new ReplyResponse(reply.getUser().getNickName(), reply.getContent(),
        reply.getCreatedAt().getMonthValue(), reply.getCreatedAt().getDayOfMonth());
  }
}
