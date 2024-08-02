package com.clody.support.exception;


import com.clody.support.dto.type.ErrorType;

public class InvalidDateFormatException extends InternalServerException {

  public InvalidDateFormatException() {
    super(ErrorType.INVALID_DATE_FORMAT);
  }

  public InvalidDateFormatException(ErrorType errorType) {
    super(errorType);
  }

}
