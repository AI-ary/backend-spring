package com.aiary.aiary.domain.user.exception;

import static com.aiary.aiary.global.error.ErrorCode.USER_EMAIL_DUPLICATED;

import com.aiary.aiary.global.error.exception.BusinessException;

public class UserDuplicatedException extends BusinessException {

    public UserDuplicatedException() {
        super(USER_EMAIL_DUPLICATED);
    }
}
