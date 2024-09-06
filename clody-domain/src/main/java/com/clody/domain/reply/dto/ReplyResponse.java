package com.clody.domain.reply.dto;

import com.clody.domain.reply.Reply;

public record ReplyResponse(
    String nickname,
    String content,
    int month,
    int date,
    Boolean isRead) {

  public static ReplyResponse of(String nickName, String content, int month, int date, boolean isRead) {
    return new ReplyResponse(nickName, content, month, date, isRead);
  }

  public static ReplyResponse from(Reply reply) {
    return new ReplyResponse(reply.getUser().getNickName(), reply.getContent(),
        reply.getDiaryCreatedDate().getMonthValue(), reply.getDiaryCreatedDate().getDayOfMonth(), reply.checkUserReadReply());
  }
}
