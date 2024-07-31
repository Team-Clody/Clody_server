package com.clody.support.exception.auth;


import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;

public class SignInException extends BusinessException {

    public SignInException(ErrorType errorType) {
        super(errorType);
    }
}
