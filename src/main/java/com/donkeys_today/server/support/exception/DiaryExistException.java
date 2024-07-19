package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.type.ErrorType;

public class DiaryExistException extends BusinessException{

  public DiaryExistException(ErrorType errorType) {
    super(errorType);
  }
}
