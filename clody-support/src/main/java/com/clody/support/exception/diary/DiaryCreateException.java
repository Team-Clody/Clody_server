package com.clody.support.exception.diary;


import com.clody.support.dto.type.ErrorType;
import com.clody.support.exception.BusinessException;

public class DiaryCreateException extends BusinessException {

    public DiaryCreateException(ErrorType errorType) {
        super(errorType);
    }
}
