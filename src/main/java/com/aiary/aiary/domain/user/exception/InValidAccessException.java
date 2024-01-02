package com.aiary.aiary.domain.user.exception;


import com.aiary.aiary.global.error.ErrorCode;
import com.aiary.aiary.global.error.exception.BusinessException;

public class InValidAccessException extends BusinessException {

    public InValidAccessException() {
        super(ErrorCode.ACCESS_INVALID_VALUE);
    }
}
