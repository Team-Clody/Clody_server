package com.clody.infra.external.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageContent {

  DIARY_WRITE_REQUEST("클로디","오늘의 감사한 일을 말해보세요!"),
  REPLY_COMPLETED_REQUEST("클로디","행운의 답장이 도착했어요!"),
  ;
  public final String title;
  public final String body;
}
