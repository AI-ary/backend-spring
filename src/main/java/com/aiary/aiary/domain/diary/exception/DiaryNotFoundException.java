package com.aiary.aiary.domain.diary.exception;

import com.aiary.aiary.global.error.ErrorCode;
import com.aiary.aiary.global.exception.BusinessException;

public class DiaryNotFoundException extends BusinessException {

    public DiaryNotFoundException(){
        super(ErrorCode.DIARY_NOT_FOUND_ERROR);
    }
}
