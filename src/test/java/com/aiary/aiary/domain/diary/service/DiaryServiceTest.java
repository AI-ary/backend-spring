package com.aiary.aiary.domain.diary.service;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.dto.response.DiaryRes;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryRes;
import com.aiary.aiary.domain.diary.dto.response.SearchDiariesRes;
import com.aiary.aiary.domain.diary.entity.Diary;
import com.aiary.aiary.domain.diary.exception.DiaryNotFoundException;
import com.aiary.aiary.domain.diary.repository.DiaryRepository;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.domain.user.repository.UserRepository;
import com.aiary.aiary.support.database.DatabaseTest;
import com.aiary.aiary.support.fixture.DiaryFixture;
import com.aiary.aiary.support.fixture.UserFixture;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;


import java.util.List;

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

    @Test
    @DisplayName("일기들을 달 별로 조회할 수 있다.")
    void findMonthlyDiaryByDate(){
        //given
        userRepository.save(UserFixture.DIARY_FIND_MONTH_USER);
        diaryRepository.saveAll(DiaryFixture.INSERT_FIND_DIARIES());

        //when
        MonthlyDiaryRes monthlyDiaryRes = diaryService.findMonthlyDiaryByDate(UserFixture.DIARY_FIND_MONTH_USERDETAIL, "2023-09");
        List<DiaryRes> diaryRes = monthlyDiaryRes.getMonthlyDiaryRes();

        //then
        assertThat(diaryRes.size()).isEqualTo(2);
        assertThat(diaryRes.get(0).getTitle()).isEqualTo("일기 제목2");
        assertThat(diaryRes.get(1).getTitle()).isEqualTo("일기 제목1");
    }

    @Test
    @DisplayName("키워드로 제목,내용을 포함한 일기들을 검색할 수 있다.")
    void searchDiariesByDate(){
        //given
        userRepository.save(UserFixture.DIARY_SEARCH_USER);
        diaryRepository.saveAll(DiaryFixture.INSERT_SEARCH_DIARIES());
        PageRequest pageRequest = PageRequest.of(0, 40);

        //when
        SearchDiariesRes searchDiariesRes = diaryService.searchDiariesByKeyword(UserFixture.DIARY_SEARCH_USERDETAIL
                , pageRequest, "2023-09", "내용");
        List<DiaryRes> diaryRes = searchDiariesRes.getDiaryRes();

        //then
        assertThat(diaryRes.size()).isEqualTo(2);
        assertThat(diaryRes.get(0).getContents()).isEqualTo("일기 내용2");
        assertThat(diaryRes.get(1).getContents()).isEqualTo("일기 내용1");
    }

}
