package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.mapper.DiaryMapper;
import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryInfo;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository ;
    private final DiaryMapper diaryMapper;

    public void createDiary(DiaryCreateRequest diaryCreateRequest){
        User findUser = userRepository.findById(diaryCreateRequest.getUserId()).orElseThrow(IllegalAccessError::new);

        Diary newDiary = diaryMapper.toCreateRequestDTO(diaryCreateRequest, findUser);
        diaryRepository.save(newDiary);
    }
  
    @Transactional
    public void deleteDiary(Long diaryId){
        Diary deleteDiary = findDiaryById(diaryId);
        deleteDiary.delete();
    }

    @Transactional(readOnly = true)
    public MonthlyDiaryInfo findMonthlyDiary(Long userId, Date diaryDate){
        List<Diary> monthlyDiaries = diaryRepository.findMonthlyDiaryByUserId(userId, diaryDate);
        return diaryMapper.toMonthlyDiaryList(monthlyDiaries);
    }

    @Transactional(readOnly = true)
    public Diary findDiaryById(Long diaryId){
        return diaryRepository.findById(diaryId).orElseThrow(DiaryNotFoundException::new);
    }

}
