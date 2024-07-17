package com.donkeys_today.server.support.exception.auth;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;

public class SignInException extends BusinessException {

    public SignInException(ErrorType errorType) {
        super(errorType);
    }
}