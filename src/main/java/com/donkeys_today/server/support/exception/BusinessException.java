package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.type.ErrorType;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  public ErrorType errorType;

  public BusinessException(ErrorType errorType) {
    super(errorType.toString());
    this.errorType = errorType;
  }
}
