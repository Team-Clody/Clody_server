package com.clody.support.exception;

import com.clody.support.dto.ApiResponse;
import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.auth.SignInException;
import com.clody.support.exception.auth.SignUpException;
import com.clody.support.exception.diary.DiaryCreateException;
import com.clody.support.exception.reply.ReplyException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(SignUpException.class)
    protected ResponseEntity<ApiResponse<?>> SignUpException(SignUpException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getErrorType()));
    }

    @ExceptionHandler(SignInException.class)
    protected ResponseEntity<ApiResponse<?>> SignUpException(SignInException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getErrorType()));
    }

    @ExceptionHandler(DiaryCreateException.class)
    protected ResponseEntity<ApiResponse<?>> DiaryCreateException(DiaryCreateException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getErrorType()));
    }

    @ExceptionHandler(ReplyException.class)
    protected ResponseEntity<ApiResponse<?>> BusinessException(ReplyException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(e.getErrorType()));
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    protected ResponseEntity<ApiResponse<?>> BusinessException(InvalidDateFormatException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(ErrorType.INVALID_DATE_FORMAT,e.getErrorType()));
    }

    @ExceptionHandler(SignatureException.class)
    protected ResponseEntity<ApiResponse<?>> BusinessException(SignatureException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(ErrorType.WRONG_SIGNATURE_TOKEN));
    }
}
