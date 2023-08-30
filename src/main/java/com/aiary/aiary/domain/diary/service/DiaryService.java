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
import com.aiary.aiary.domain.user.exception.UserNotFoundException;
import com.aiary.aiary.domain.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {
    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;
    private final DiaryMapper diaryMapper;

    public void createDiary(User user, DiaryCreateRequest diaryCreateRequest){
        Diary newDiary = diaryMapper.toCreateRequestDTO(diaryCreateRequest, user);
        diaryRepository.save(newDiary);
    }
  
    @Transactional
    public void deleteDiary(UserDetail userDetail,  Long diaryId){
        Diary deleteDiary = diaryRepository.findDiaryWithUser(diaryId).orElseThrow(DiaryNotFoundException::new);
        User user = userRepository.findUserByEmail(userDetail.getUsername()).orElseThrow(UserNotFoundException::new);

        if (!Objects.equals(user.getId(), deleteDiary.getUser().getId()))
            throw new UnAuthorizedAccessException();

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
