package com.clody.support.exception.reply;

import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;

public class ReplyException extends BusinessException {

  public ReplyException(ErrorType errorType) {
    super(errorType);
  }
}
