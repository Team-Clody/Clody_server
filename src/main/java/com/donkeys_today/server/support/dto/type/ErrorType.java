package com.donkeys_today.server.support.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

  /**
   * 인증 / 인가 관련 오류
   */
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "사용자의 로그인 검증을 실패했습니다."),
  EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
  EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "엑세스 토큰이 만료되었습니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 만료되었습니다."),
  INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 액세스 토큰입니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 리프레시 토큰입니다."),
  MISMATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 일치하지 않습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 토큰입니다."),
  UNKNOWN_TOKEN(HttpStatus.UNAUTHORIZED.value(), "인증 토큰이 존재하지 않습니다."),
  UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "지원하지 않는 토큰 방식입니다."),
  WRONG_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Signature가 잘못된 Token입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"),
  INVALID_PLATFORM_TYPE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "지원하지 않는 로그인 플랫폼입니다."),
  DUPLICATED_USER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미 가입된 유저입니다."),

  /**
   * 회원 관련 오류
   */
  USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"존재하지 않는 사용자입니다."),
  ;
  private final int status;
  private final String message;

}
