package com.aiary.aiary.domain.diary.dto.mapper;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.response.SearchDiariesRes;
import com.aiary.aiary.domain.diary.dto.response.DiaryInfo;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryInfo;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.Weather;
import com.aiary.aiary.domain.user.entity.User;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryMapper {

    public Diary toCreateRequestDTO(DiaryCreateRequest diaryCreateRequest, User user){
        return Diary.builder()
                .title(diaryCreateRequest.getTitle())
                .contents(diaryCreateRequest.getContents())
                .weather(Weather.valueOf(diaryCreateRequest.getWeather()))
                .emoji(diaryCreateRequest.getEmoji())
                .drawingUrl(diaryCreateRequest.getDrawingUrl())
                .diaryDate(diaryCreateRequest.getDiaryDate())
                .user(user)
                .build();
    }

    public DiaryInfo toEntity(Diary diary){
        return DiaryInfo.builder()
                .title(diary.getTitle())
                .weather(diary.getWeather())
                .emoji(diary.getEmoji())
                .contents(diary.getContents())
                .diaryDate(diary.getDiaryDate().toString())
                .drawingUrl(diary.getDrawingUrl())
                .build();
    }

    public MonthlyDiaryInfo toMonthlyDiaryList(List<Diary> monthlyDiaries){
        List<DiaryInfo> diaryInfos = monthlyDiaries.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
        return MonthlyDiaryInfo.builder().monthlyDiaryInfo(diaryInfos).build();
    }

    public SearchDiariesRes toDiarySlice(Slice<Diary> diaries){
        List<DiaryInfo> diaryInfos = diaries.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());

        return SearchDiariesRes.builder()
                .diaryInfos(diaryInfos)
                .curPageNumber(diaries.getNumber())
                .hasNext(diaries.hasNext())
                .hasPrevious(diaries.hasPrevious())
                .build();
    }




}
