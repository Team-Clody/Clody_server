package com.donkeys_today.server.support.exception.reply;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;

public class ReplyException extends BusinessException {

  public ReplyException(ErrorType errorType) {
    super(errorType);
  }
}
