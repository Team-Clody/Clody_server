package com.donkeys_today.server.support.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SuccessType {

  CREATED_SUCCESS(HttpStatus.CREATED.value(), "생성이 완료되었습니다."),
  OK_SUCCESS(HttpStatus.OK.value(), "정상 요청 완료."),;

  private final int status;
  private final String message;
}
