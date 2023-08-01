package com.aiary.aiary.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    //Diary
    DIARY_CREATE_SUCCESS("D001", "그림일기 등록 성공"),
    DIARY_DELETE_SUCCESS("D002", "그림일기 삭제 성공"),
    DIARY_READ_SUCCESS("D003", "그림일기 조회 성공");


    private final String code;
    private final String message;
}
