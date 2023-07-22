package com.aiary.aiary.domain.diary.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@RequiredArgsConstructor
public class DiaryCreateRequest {

    @NotNull(message = "사용자 Id는 필수 입니다.")
    private int userId;

    @NotBlank(message = "일기 제목은 필수 입니다.")
    private String title;

    @NotBlank(message = "일기 날씨는 필수 입니다.")
    private String weather;

    @NotBlank(message = "일기 이모티콘은 필수 입니다.")
    private String emoji;

    @NotBlank(message = "일기 내용은 필수 입니다.")
    private String contents;

    @NotNull(message = "일기 날짜는 필수 입니다.")
    private Date diaryDate;

    @NotNull(message = "일기 주소는 필수 입니다.")
    private String drawingUrl;

}
