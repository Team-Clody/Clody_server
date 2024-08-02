package com.clody.support.exception.auth;


import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;

public class SignUpException extends BusinessException {

    public SignUpException(ErrorType errorType) {
        super(errorType);
    }
}
