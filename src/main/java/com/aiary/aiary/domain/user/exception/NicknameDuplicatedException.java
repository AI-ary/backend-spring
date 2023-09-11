package com.aiary.aiary.domain.user.exception;

import com.aiary.aiary.global.error.ErrorCode;
import com.aiary.aiary.global.error.exception.BusinessException;

public class NicknameDuplicatedException extends BusinessException {
    public NicknameDuplicatedException() {
        super(ErrorCode.USER_NICKNAME_DUPLICATED);
    }
}
