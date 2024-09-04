package com.clody.domain.reply.dto;

import com.clody.domain.reply.Reply;
import com.clody.domain.reply.ReplyType;

public record ReplyResponse(
    String nickname,
    String content,
    int month,
    int date,
    ReplyType replyType
) {

  public static ReplyResponse of(String nickName, String content, int month, int date, ReplyType replyType) {
    return new ReplyResponse(nickName, content, month, date, replyType);
  }

  public static ReplyResponse from(Reply reply) {
    return new ReplyResponse(reply.getUser().getNickName(), reply.getContent(),
        reply.getCreatedAt().getMonthValue(), reply.getCreatedAt().getDayOfMonth(), reply.getReplyType());
  }
}
