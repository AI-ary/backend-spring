package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.Weather;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository ;

    public void createDiary(DiaryCreateRequest diaryCreateRequest){
        User curUser = userRepository.findById((long) diaryCreateRequest.getUserId()).orElseThrow(DiaryNotFoundException::new);

        Diary newDiary = Diary.builder()
                .title(diaryCreateRequest.getTitle())
                .contents(diaryCreateRequest.getContents())
                .weather(Weather.valueOf(diaryCreateRequest.getWeather()))
                .emoji(diaryCreateRequest.getEmoji())
                .drawingUrl(diaryCreateRequest.getDrawingUrl())
                .diaryDate(diaryCreateRequest.getDiaryDate())
                .user(curUser)
                .build();
        diaryRepository.save(newDiary);
    }
}
