package com.clody.clodyapi.presentation.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReplyType {

  STATIC_REPLY("정적 답변"),
  DYNAMIC_REPLY("동적 답변"),
  NO_REPLY("답변 없음");

  private final String replyType;
}
