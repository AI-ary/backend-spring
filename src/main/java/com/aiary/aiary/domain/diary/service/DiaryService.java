package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.mapper.DiaryMapper;
import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryRes;
import com.aiary.aiary.domain.diary.dto.response.SearchDiariesRes;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.entity.DiaryDocument;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryJpaRepository;
import com.aiary.aiary.domain.diary.repository.DiarySearchRepositoryImpl;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.exception.UnAuthorizedAccessException;
import com.aiary.aiary.domain.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {

    private static final String FIRST_DAY = "-01";
    private final UserService userService;
    private final DiaryJpaRepository diaryJpaRepository;
    private final DiarySearchRepositoryImpl diarySearchRepository;
    private final DiaryMapper diaryMapper;

    public void createDiary(UserDetail userDetail, DiaryCreateReq diaryCreateReq, String drawingUrl){
        Diary newDiary = diaryMapper.toCreateRequestDTO(diaryCreateReq, userDetail.getUser(), drawingUrl);
        diaryJpaRepository.save(newDiary);
    }
  
    @Transactional
    public void deleteDiary(UserDetail userDetail,  Long diaryId){
        Diary deleteDiary = findDiaryWithUser(diaryId);
        User user = userService.findUserById(userDetail.getUserId());

        if (!Objects.equals(user.getId(), deleteDiary.getUser().getId()))
            throw new UnAuthorizedAccessException();

        deleteDiary.delete();
    }

    @Transactional(readOnly = true)
    public MonthlyDiaryRes findMonthlyDiaryByDate(UserDetail userDetail, String diaryDate){
        Long userId = userDetail.getUserId();
        LocalDate monthDate = LocalDate.parse(diaryDate + FIRST_DAY);
        List<Diary> monthlyDiaries = diaryJpaRepository.findMonthlyDiaryByUserId(userId, monthDate);
        return diaryMapper.toMonthlyDiaryList(monthlyDiaries);
    }

    @Transactional(readOnly = true)
    public Diary findDiaryWithUser(Long diaryId) {
        return diaryJpaRepository.findDiaryWithUser(diaryId).orElseThrow(DiaryNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public SearchDiariesRes searchDiariesByKeyword(UserDetail userDetail, PageRequest pageRequest, String diaryDate, String keyword) {
        Long userId = userDetail.getUserId();
        LocalDate monthDate = LocalDate.parse(diaryDate + FIRST_DAY);
        Page<DiaryDocument> diariesSearchByKeyword = diarySearchRepository.searchByKeyword(userId, monthDate, keyword, pageRequest);

        return diaryMapper.toSearchDiaryList(diariesSearchByKeyword);
    }
}
