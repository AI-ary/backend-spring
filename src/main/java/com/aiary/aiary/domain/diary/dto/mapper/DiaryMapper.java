package com.aiary.aiary.domain.diary.dto.mapper;


import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.dto.response.DiaryRes;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryRes;
import com.aiary.aiary.domain.diary.dto.response.SearchDiariesRes;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.DiaryDocument;
import com.aiary.aiary.domain.diary.entity.Weather;
import com.aiary.aiary.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryMapper {

    public Diary toCreateRequestDTO(DiaryCreateReq diaryCreateReq, User user, String drawingUrl){
        return Diary.builder()
                .title(diaryCreateReq.getTitle())
                .contents(diaryCreateReq.getContents())
                .weather(Weather.valueOf(diaryCreateReq.getWeather()))
                .emoji(diaryCreateReq.getEmoji())
                .drawingUrl(drawingUrl)
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

    public DiaryRes toDocument(DiaryDocument diary){
        return DiaryRes.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .weather(Weather.valueOf(diary.getWeather()))
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

    public SearchDiariesRes toSearchDiaryList (Page<DiaryDocument> diaries){
        List<DiaryRes> diaryInfos = diaries.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());

        return SearchDiariesRes.builder()
                .diaryInfos(diaryInfos)
                .curPageNumber(diaries.getNumber())
                .hasNext(diaries.hasNext())
                .hasPrevious(diaries.hasPrevious())
                .build();
    }
}
