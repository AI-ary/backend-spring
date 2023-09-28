package com.aiary.aiary.support.fixture;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.dto.response.DiaryRes;
import com.aiary.aiary.domain.diary.entity.Weather;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DiaryControllerFixture {

    public static final DiaryCreateReq CREATE_REQ = DiaryCreateReq.builder()
            .title("일기 제목")
            .contents("일기 내용")
            .diaryDate(LocalDate.of(2023, 9, 24))
            .weather("SUNNY")
            .emoji("\uD83D\uDCAA")
            .build();

    public static List<DiaryRes> MONTHLY_DIARIES() {
        return IntStream.range(0, 5)
                .mapToObj(i -> DiaryRes.builder()
                        .diaryId((long) i)
                        .title("일기 제목" + i)
                        .weather(Weather.SUNNY)
                        .emoji("\uD83D\uDCAA")
                        .contents("일기 내용" + i)
                        .drawingUrl("url" + i)
                        .diaryDate("2023-09-0" + (i + 1))
                        .build())
                .collect(Collectors.toList());
    }
}
