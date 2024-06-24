package com.donkeys_today.server.common.exception;

import com.donkeys_today.server.common.dto.type.ErrorType;

public class NotFoundException extends BusinessException{

  public NotFoundException(ErrorType errorType) {
    super(errorType);
  }
}
