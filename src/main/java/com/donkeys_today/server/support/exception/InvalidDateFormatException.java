package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.type.ErrorType;

public class InvalidDateFormatException extends InternalServerException {

  public InvalidDateFormatException() {
    super(ErrorType.INVALID_DATE_FORMAT);
  }

  public InvalidDateFormatException(ErrorType errorType) {
    super(errorType);
  }

}
