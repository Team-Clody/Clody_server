package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.type.ErrorType;

public class UnauthorizedException extends BusinessException{

    public UnauthorizedException(ErrorType errorType) {

        super(errorType);
    }
}
