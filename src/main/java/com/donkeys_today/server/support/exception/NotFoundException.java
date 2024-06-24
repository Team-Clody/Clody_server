package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.type.ErrorType;

public class NotFoundException extends BusinessException{

  public NotFoundException(ErrorType errorType) {
    super(errorType);
  }
}
