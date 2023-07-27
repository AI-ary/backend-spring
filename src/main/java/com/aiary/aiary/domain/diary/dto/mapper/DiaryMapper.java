package com.aiary.aiary.domain.diary.dto.mapper;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.response.DiaryInfo;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryInfo;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.Weather;
import com.aiary.aiary.domain.user.entity.User;
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
                .diaryDate(diary.getDiaryDate())
                .drawingUrl(diary.getDrawingUrl())
                .build();
    }




}
