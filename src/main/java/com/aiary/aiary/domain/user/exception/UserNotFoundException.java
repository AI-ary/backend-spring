package com.aiary.aiary.domain.user.exception;

import com.aiary.aiary.global.error.ErrorCode;
import com.aiary.aiary.global.error.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND_ERROR);
    }
}
