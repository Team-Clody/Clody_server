package com.clody.support.exception;

import com.clody.support.dto.type.ErrorType;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  public ErrorType errorType;

  public BusinessException(ErrorType errorType) {
    super(errorType.toString());
    this.errorType = errorType;
  }
}
