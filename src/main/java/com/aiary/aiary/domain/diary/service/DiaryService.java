package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.mapper.DiaryMapper;
import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryInfo;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.User;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.exception.UnAuthorizedAccessException;
import com.aiary.aiary.domain.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
    private final DiaryRepository diaryRepository;
    private final DiaryMapper diaryMapper;

    public void createDiary(UserDetail userDetail, DiaryCreateRequest diaryCreateRequest){
        Diary newDiary = diaryMapper.toCreateRequestDTO(diaryCreateRequest, userDetail.getUser());
        diaryRepository.save(newDiary);
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
    public MonthlyDiaryInfo findMonthlyDiary(UserDetail userDetail, String diaryDate){
        Long userId = userDetail.getUserId();
        LocalDate monthDate = LocalDate.parse(diaryDate + FIRST_DAY);
        List<Diary> monthlyDiaries = diaryRepository.findMonthlyDiaryByUserId(userId, monthDate);
        return diaryMapper.toMonthlyDiaryList(monthlyDiaries);
    }

    @Transactional(readOnly = true)
    public Diary findDiaryWithUser(Long diaryId) {
        return diaryRepository.findDiaryWithUser(diaryId).orElseThrow(DiaryNotFoundException::new);
    }

}
