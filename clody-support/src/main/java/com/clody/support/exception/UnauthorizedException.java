package com.clody.support.exception;


import com.clody.support.dto.type.ErrorType;

public class UnauthorizedException extends BusinessException{

    public UnauthorizedException(ErrorType errorType) {
        super(errorType);
    }
}
