package com.donkeys_today.server.support.exception;

import com.donkeys_today.server.support.dto.ApiResponse;
import com.donkeys_today.server.support.dto.type.ErrorType;
import com.donkeys_today.server.support.exception.auth.SignInException;
import com.donkeys_today.server.support.exception.auth.SignUpException;
import com.donkeys_today.server.support.exception.reply.ReplyException;
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

    @ExceptionHandler(SignInException.class)
    protected ResponseEntity<ApiResponse> SignUpException(SignInException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getErrorType()));
    }

    @ExceptionHandler(ReplyException.class)
    protected ResponseEntity<ApiResponse<?>> BusinessException(ReplyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getErrorType()));
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    protected ResponseEntity<ApiResponse<?>> BusinessException(InvalidDateFormatException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ErrorType.INVALID_DATE_FORMAT,e.getErrorType()));
    }

    @ExceptionHandler(DiaryExistException.class)
    protected ResponseEntity<ApiResponse<?>> DiaryExistException(DiaryExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ErrorType.DIARY_ALREADY_EXIST,e.getErrorType()));
    }

}