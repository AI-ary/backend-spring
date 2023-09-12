package com.aiary.aiary.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** {주체}_{이유} message 는 동사 명사형으로 마무리 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
  // Global
  INTERNAL_SERVER_ERROR(500, "G001", "서버 오류"),
  INPUT_INVALID_VALUE(409, "G002", "잘못된 입력"),
  ACCESS_INVALID_VALUE(400, "G003", "잘못된 접근"),

  // 예시
  // User 도메인
  INVALID_PASSWORD(400, "U001", "잘못된 비밀번호"),
  USER_NOT_FOUND_ERROR(400, "U002", "존재하지 않는 사용자"),
  UNAUTHORIZED_ACCESS_ERROR(403, "U003", "권한이 없는 사용자"),
  USER_EMAIL_DUPLICATED(409, "U004", "회원 아이디 중복"),
  USER_NICKNAME_DUPLICATED(409, "U005", "회원 닉네임 중복"),

  // 다이어리
  DIARY_NOT_FOUND_ERROR(400, "D001", "존재하지 않는 일기");

  private final int status;
  private final String code;
  private final String message;
}
