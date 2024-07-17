package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.exception.auth.SignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(SignUpException.class)
    protected ResponseEntity<ApiResponse> SignUpException(SignUpException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getErrorType()));
    }
}
