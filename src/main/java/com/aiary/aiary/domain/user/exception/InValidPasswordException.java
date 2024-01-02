package com.aiary.aiary.domain.user.exception;


import com.aiary.aiary.global.error.ErrorCode;
import com.aiary.aiary.global.error.exception.BusinessException;

public class InValidPasswordException extends BusinessException {

    public InValidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
