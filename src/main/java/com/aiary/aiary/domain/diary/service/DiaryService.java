package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.mapper.DiaryMapper;
import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.response.SearchDiariesRes;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryInfo;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {

    private static final String FIRST_DAY = "-01";
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
    public SearchDiariesRes searchDiariesByKeyword(UserDetail userDetail, PageRequest pageRequest, String diaryDate, String keyword) {
        Long userId = userDetail.getUserId();
        LocalDate monthDate = LocalDate.parse(diaryDate + FIRST_DAY);
        Slice<Diary> diariesSearchByKeyword = diaryRepository.searchDiariesByKeyword(userId, pageRequest, monthDate, keyword);
        return diaryMapper.toDiarySlice(diariesSearchByKeyword);
    }
}
