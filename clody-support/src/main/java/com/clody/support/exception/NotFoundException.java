package com.clody.support.exception;


import com.clody.support.dto.type.ErrorType;

public class NotFoundException extends BusinessException{

  public NotFoundException(ErrorType errorType) {
    super(errorType);
  }
}
