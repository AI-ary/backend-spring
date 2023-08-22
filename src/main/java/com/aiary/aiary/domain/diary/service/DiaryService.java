package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.mapper.DiaryMapper;
import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.response.DiariesSearchByKeyword;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryInfo;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryMapper diaryMapper;

    public void createDiary(User user, DiaryCreateRequest diaryCreateRequest){
        Diary newDiary = diaryMapper.toCreateRequestDTO(diaryCreateRequest, user);
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

    @Transactional(readOnly = true)
    public DiariesSearchByKeyword searchDiariesByKeyword(Long userId, int page, int size, String keyword) {
        PageRequest pageRequest = PageRequest.of(page, size);
        System.out.println(keyword);
        System.out.println("userId = " + userId);
        Slice<Diary> diariesSearchByKeyword = diaryRepository.searchDiariesByKeyword(userId, pageRequest, keyword);
        return diaryMapper.toDiarySlice(diariesSearchByKeyword);
    }
}
