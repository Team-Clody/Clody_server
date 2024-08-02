package com.clody.infra.external.fcm;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MessageContent {

  DIARY_WRITE_REQUEST("클로디","오늘의 일기를 작성해주세요"),
  REPLY_COMPLETED_REQUEST("클로디","답변 도착 ! 확인해보세요 "),
  ;
  public final String title;
  public final String body;
}
