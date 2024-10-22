package com.clody.support.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

  /**
   *  시간 관련 오류
   */
  INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 날짜 형식입니다."),

  /**
   * 인증 / 인가 관련 오류
   */
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "사용자의 로그인 검증을 실패했습니다."),
  EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
  EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "엑세스 토큰이 만료되었습니다."),
  EXPIRED_ID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "ID토큰이 만료되었습니다."),
  EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "토큰이 만료되었습니다."),
  INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 액세스 토큰입니다."),
  INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 리프레시 토큰입니다."),
  MISMATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED.value(), "리프레시 토큰이 일치하지 않습니다."),
  INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 토큰 형식입니다."),
  INVALID_ID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "올바르지 않은 ID토큰입니다."),
  UNKNOWN_TOKEN(HttpStatus.UNAUTHORIZED.value(), "인증 토큰이 존재하지 않습니다."),
  UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "지원하지 않는 토큰 방식입니다."),
  WRONG_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED.value(), "Signature가 잘못된 Token입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 오류"),
  INVALID_PLATFORM_TYPE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "지원하지 않는 로그인 플랫폼입니다."),
  DUPLICATED_USER_ERROR(HttpStatus.BAD_REQUEST.value(), "이미 가입된 유저입니다."),
  NOTFOUND_USER_ERROR(HttpStatus.NOT_FOUND.value(), "존재하지 않는 유저 입니다."),
  UNABLE_TO_CREATE_APPLE_PUBLIC_KEY(HttpStatus.UNAUTHORIZED.value(), "애플 로그인 중 퍼블릭 키 생성에 문제가 발생했습니다."),
  NOT_STARTS_WITH_BEARER(HttpStatus.UNAUTHORIZED.value(), "Authorization 의 토큰이 Bearer 로 시작하지 않습니다."),
  /**
   * 회원 관련 오류
   */
  USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(),"존재하지 않는 사용자입니다."),

  /**
   * 알림 관련 오류
   */
  FAIL_DIARY_ALARM_REGISTER(HttpStatus.INTERNAL_SERVER_ERROR.value(), "알람 설정에 실패했습니다."),
  INVALID_TIME_FORMAT(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 시간 형식입니다."),
  EXCESS_DIARY_CREATE(HttpStatus.BAD_REQUEST.value(), "일기는 하루에 한번만 쓸 수 있습니다."),

  /**
   * 일기 관련 오류,
   */
  DIARY_MESSAGE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "일기 데이터가 존재하지 않습니다."),

  /**
   * 답장 관련 오류,
   */
  REPLY_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "답장이 존재하지 않습니다."),
  DUPLICATE_REPLY(HttpStatus.BAD_REQUEST.value(), "중복된 답장이 존재합니다."),
  USER_UPDATED_DIARY(HttpStatus.BAD_REQUEST.value(), "사용자가 수정한 일기입니다."),
  INVALID_REPLY_TYPE(HttpStatus.BAD_REQUEST.value(), "올바르지 않은 답장 타입입니다."),
  /*
    * 알람 관련 오류
   */
  ALARM_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "알람이 존재하지 않습니다."),
  USER_NOT_AGREED_FOR_REPLY_ALARM(HttpStatus.BAD_REQUEST.value(), "사용자가 답장 알람 수신에 동의하지 않았습니다."),
  ;
  private final int status;
  private final String message;

}
