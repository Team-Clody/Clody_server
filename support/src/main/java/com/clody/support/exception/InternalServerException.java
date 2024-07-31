package com.clody.support.exception;


import com.clody.support.dto.type.ErrorType;

public class InternalServerException extends BusinessException {

    public InternalServerException() {
        super(ErrorType.INTERNAL_SERVER_ERROR);
    }

    public InternalServerException(ErrorType errorType) {
        super(errorType);
    }

}
