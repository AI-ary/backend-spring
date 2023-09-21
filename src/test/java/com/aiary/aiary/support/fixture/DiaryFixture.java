package com.aiary.aiary.support.fixture;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.Weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DiaryFixture {

    public static final Diary CREATE_DIARY = Diary.builder()
            .title("일기 제목")
            .weather(Weather.SUNNY)
            .emoji("\uD83D\uDCAA")
            .contents("일기 내용")
            .drawingUrl("url")
            .diaryDate(LocalDate.of(2023,9,19))
            .user(UserFixture.DIARY_CREATE_USER)
            .build();

    public static final Diary DELETE_DIARY = Diary.builder()
            .title("일기 제목")
            .weather(Weather.SUNNY)
            .emoji("\uD83D\uDCAA")
            .contents("일기 내용")
            .drawingUrl("url")
            .diaryDate(LocalDate.of(2023,9,20))
            .user(UserFixture.DIARY_DELETE_USER)
            .build();

    public static final Diary DELETE_UNAUTHOR_DIARY = Diary.builder()
            .title("일기 제목")
            .weather(Weather.SUNNY)
            .emoji("\uD83D\uDCAA")
            .contents("일기 내용")
            .drawingUrl("url")
            .diaryDate(LocalDate.of(2023,9,20))
            .user(UserFixture.DIARY_DELETE_USER)
            .build();

    public static final DiaryCreateReq DIARY_CREATE_REQ = DiaryCreateReq.builder()
            .title("일기 제목")
            .weather("SUNNY")
            .emoji("\uD83D\uDCAA")
            .contents("일기 내용")
            .diaryDate(LocalDate.of(2023,9,19))
            .build();

    public static List<Diary> INSERT_FIND_DIARIES() {
        List<Diary> diaries = new ArrayList<>();
        Diary diary1 = Diary.builder()
                .title("일기 제목1")
                .weather(Weather.SUNNY)
                .emoji("\uD83D\uDCAA")
                .contents("일기 내용1")
                .drawingUrl("url1")
                .diaryDate(LocalDate.of(2023,9,21))
                .user(UserFixture.DIARY_FIND_MONTH_USER)
                .build();

        Diary diary2 = Diary.builder()
                .title("일기 제목2")
                .weather(Weather.SUNNY)
                .emoji("\uD83D\uDCAA")
                .contents("일기 내용2")
                .drawingUrl("url1")
                .diaryDate(LocalDate.of(2023,9,22))
                .user(UserFixture.DIARY_FIND_MONTH_USER)
                .build();

        diaries.add(diary1);
        diaries.add(diary2);

        return diaries;
    }

    public static List<Diary> INSERT_SEARCH_DIARIES() {
        List<Diary> diaries = new ArrayList<>();
        Diary diary1 = Diary.builder()
                .title("일기 제목1")
                .weather(Weather.SUNNY)
                .emoji("\uD83D\uDCAA")
                .contents("일기 내용1")
                .drawingUrl("url1")
                .diaryDate(LocalDate.of(2023,9,21))
                .user(UserFixture.DIARY_SEARCH_USER)
                .build();

        Diary diary2 = Diary.builder()
                .title("일기 제목2")
                .weather(Weather.SUNNY)
                .emoji("\uD83D\uDCAA")
                .contents("일기 내용2")
                .drawingUrl("url1")
                .diaryDate(LocalDate.of(2023,9,22))
                .user(UserFixture.DIARY_SEARCH_USER)
                .build();

        diaries.add(diary1);
        diaries.add(diary2);

        return diaries;
    }
}
