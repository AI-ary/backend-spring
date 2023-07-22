package com.aiary.aiary.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    //Diary
    DIARY_CREATE_SUCCESS("D001", "그림일기 등록 성공");

    private final String code;
    private final String message;
}
