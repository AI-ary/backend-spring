package com.aiary.aiary.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 다이어리
    DIARY_NOT_FOUND_ERROR(400, "D001", "존재하지 않는 일기입니다.");



    private final int status;
    private final String code;
    private final String message;
}
