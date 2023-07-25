package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.mapper.DiaryMapper;
import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository ;
    private final DiaryMapper diaryMapper;

    public void createDiary(DiaryCreateRequest diaryCreateRequest){
        User findUser = userRepository.findById(diaryCreateRequest.getUserId()).orElseThrow(IllegalAccessError::new);

        Diary newDiary = diaryMapper.mapCreateRequestToEntity(diaryCreateRequest, findUser);
        diaryRepository.save(newDiary);
    }
}
