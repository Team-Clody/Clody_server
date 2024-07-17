package com.donkeys_today.server.support.exception.auth;

import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.BusinessException;
import lombok.Getter;

public class SignUpException extends BusinessException {

    public SignUpException(ErrorType errorType) {
        super(errorType);
    }
}