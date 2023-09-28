package com.aiary.aiary.support.fixture;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;

import java.time.LocalDate;

public class DiaryControllerFixture {

    public static final DiaryCreateReq CREATE_REQ = DiaryCreateReq.builder()
            .title("일기 제목")
            .contents("일기 내용")
            .diaryDate(LocalDate.of(2023, 9, 24))
            .weather("SUNNY")
            .emoji("\uD83D\uDCAA")
            .build();
}
