package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.repository.UserRepository;
import com.aiary.aiary.support.database.DatabaseTest;
import com.aiary.aiary.support.fixture.DiaryFixture;
import com.aiary.aiary.support.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void saveUser(){
        userRepository.save(UserFixture.TEST_USER);
    }

    @Test
    @DisplayName("일기가 생성을 확인한다.")
    void createDiary(){
        //given
        UserDetail userDetail = UserFixture.TEST_USERDETAIL;
        DiaryCreateReq diaryCreateReq = DiaryFixture.TEST_DIARY_CREATE_REQ;
        String diaryUrl = "url";
        Diary expect = diaryRepository.save(DiaryFixture.TEST_DIARY);

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
}
