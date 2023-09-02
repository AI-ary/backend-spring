package com.aiary.aiary.domain.diary.dto.mapper;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.dto.response.DiaryRes;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryRes;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.Weather;
import com.aiary.aiary.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryMapper {

    public Diary toCreateRequestDTO(DiaryCreateReq diaryCreateReq, User user){
        return Diary.builder()
                .title(diaryCreateReq.getTitle())
                .contents(diaryCreateReq.getContents())
                .weather(Weather.valueOf(diaryCreateReq.getWeather()))
                .emoji(diaryCreateReq.getEmoji())
                .drawingUrl(diaryCreateReq.getDrawingUrl())
                .diaryDate(diaryCreateReq.getDiaryDate())
                .user(user)
                .build();
    }

    public DiaryRes toEntity(Diary diary){
        return DiaryRes.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .weather(diary.getWeather())
                .emoji(diary.getEmoji())
                .contents(diary.getContents())
                .diaryDate(diary.getDiaryDate().toString())
                .drawingUrl(diary.getDrawingUrl())
                .build();
    }

    public MonthlyDiaryRes toMonthlyDiaryList(List<Diary> monthlyDiaries){
        List<DiaryRes> diaryRes = monthlyDiaries.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return MonthlyDiaryRes.builder().monthlyDiaryRes(diaryRes).build();
    }




}
