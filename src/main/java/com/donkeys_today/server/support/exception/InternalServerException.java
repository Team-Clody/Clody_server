package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.type.ErrorType;

public class InternalServerException extends BusinessException {

    public InternalServerException() {
        super(ErrorType.INTERNAL_SERVER);
    }

    public InternalServerException(ErrorType errorType) {
        super(errorType);
    }

}
