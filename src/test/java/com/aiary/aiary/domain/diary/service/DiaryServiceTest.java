package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.repository.UserRepository;
import com.aiary.aiary.support.database.DatabaseTest;
import com.aiary.aiary.support.fixture.DiaryFixture;
import com.aiary.aiary.support.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.*;
@DatabaseTest
@DisplayName("Diary 서비스의 ")
class DiaryServiceTest {

    @Autowired
    private DiaryService diaryService;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("일기가 생성을 확인한다.")
    void createDiary(){
        //given
        userRepository.save(UserFixture.DIARY_CREATE_USER);
        UserDetail userDetail = UserFixture.DIARY_CREATE_USERDETAIL;
        DiaryCreateReq diaryCreateReq = DiaryFixture.DIARY_CREATE_REQ;
        String diaryUrl = "url";
        Diary expect = diaryRepository.save(DiaryFixture.CREATE_DIARY);

        //when
        diaryService.createDiary(userDetail, diaryCreateReq, diaryUrl);
        Diary actual = diaryService.findDiaryWithUser(expect.getId());

        //then
        assertAll(
            () -> assertThat(actual.getContents()).isEqualTo(expect.getContents()),
            () -> assertThat(actual.getContents()).isEqualTo(expect.getContents()),
            () -> assertThat(actual.getDiaryDate()).isEqualTo(expect.getDiaryDate()),
            () -> assertThat(actual.getEmoji()).isEqualTo(expect.getEmoji()),
            () -> assertThat(actual.getDrawingUrl()).isEqualTo(expect.getDrawingUrl()),
            () -> assertThat(actual.getWeather()).isEqualTo(expect.getWeather())
        );
    }

    @Test
    @DisplayName("일기가 PK로 삭제가 되는지 확인한다.")
    void deleteDiary(){
        //given
        userRepository.save(UserFixture.DIARY_DELETE_USER);
        Diary expect = diaryRepository.save(DiaryFixture.DELETE_DIARY);

        //when
        diaryService.deleteDiary(UserFixture.DIAEY_DELETE_USERDETAIL, expect.getId());

        //then
        assertAll(
                () ->  assertThat(expect.isDeleted()).isTrue(),
                () ->  assertThatThrownBy(() -> diaryService.findDiaryWithUser(expect.getId()))
                        .isInstanceOf(DiaryNotFoundException.class)
        );


    }
}
