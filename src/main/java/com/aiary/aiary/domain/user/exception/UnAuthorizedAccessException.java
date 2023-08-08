package com.aiary.aiary.domain.user.exception;


import com.aiary.aiary.global.error.exception.BusinessException;

import static com.aiary.aiary.global.error.ErrorCode.UNAUTHORIZED_ACCESS_ERROR;

public class UnAuthorizedAccessException extends BusinessException {
    public UnAuthorizedAccessException() {
        super(UNAUTHORIZED_ACCESS_ERROR);
    }
}
