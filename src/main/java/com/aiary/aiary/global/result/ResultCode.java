package com.aiary.aiary.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
  // User
  USER_REGISTRATION_SUCCESS("U001", "사용자 등록 성공"),
  USER_EMAIL_DUPLICATED("U002", "회원 아이디 중복"),
  USER_EMAIL_NOT_DUPLICATED("U003", "회원 아이디 중복되지 않음"),
  USER_LOGIN_SUCCESS("U004", "회원 로그인 성공"),
  USER_LOGOUT_SUCCESS("U005", "회원 로그아웃 성공"),
  USER_REISSUE_SUCCESS("U006", "토큰 재발급 성공"),
  USER_UPDATE_THEME_SUCCESS("U007", "회원 테마 변경 성공"),

  // Diary
  DIARY_CREATE_SUCCESS("D001", "그림일기 등록 성공"),
  DIARY_DELETE_SUCCESS("D002", "그림일기 삭제 성공"),
  DIARY_READ_SUCCESS("D003", "그림일기 조회 성공");
  

  private final String code;
  private final String message;
}