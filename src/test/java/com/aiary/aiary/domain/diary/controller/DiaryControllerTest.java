package com.aiary.aiary.domain.diary.controller;

import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.dto.response.DiaryRes;
import com.aiary.aiary.domain.diary.dto.response.MonthlyDiaryRes;
import com.aiary.aiary.domain.diary.service.DiaryService;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.global.s3.service.S3UploadService;
import com.aiary.aiary.support.fixture.DiaryControllerFixture;
import com.aiary.aiary.support.utils.MockApiTest;
import com.aiary.aiary.support.utils.WithCustomMockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.aiary.aiary.support.docs.ApiDocumentUtils.getDocumentRequest;
import static com.aiary.aiary.support.docs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DiaryController.class)
@ExtendWith(RestDocumentationExtension.class)
@DisplayName("Diary 컨트롤러에 ")
class DiaryControllerTest extends MockApiTest {

    @MockBean
    private DiaryService diaryService;
    @MockBean
    private S3UploadService s3UploadService;

    @Test
    @WithCustomMockUser
    @DisplayName("일기가 등록될 수 있다.")
    void createDiary() throws Exception {
        //given
        MockMultipartFile file = new MockMultipartFile(
                "file",      // 파라미터 이름
                "file_name", // 파일 이름
                "image/jpeg",// 파일 타입
                "test file".getBytes(StandardCharsets.UTF_8) // 파일 내용
        );
        given(s3UploadService.saveFile(any(), any())).willReturn(String.valueOf(file));

        DiaryCreateReq createReq = DiaryControllerFixture.CREATE_REQ;
        String jsonByCreateReq = objectMapper.writeValueAsString(createReq);
        MockMultipartFile request = new MockMultipartFile(
                "createRequest",
                "createRequest",
                "application/json",
                jsonByCreateReq.getBytes(StandardCharsets.UTF_8)
        );
        String token = "accessToken";
        diaryService.createDiary(any(), any(), any());

        //when
        ResultActions perform =
                mockMvc.perform(
                        multipart("/diaries")
                                .file(file)
                                .file(request)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                );

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("register diary",
                        getDocumentRequest(),
                        getDocumentResponse()));

    }

    @Test
    @DisplayName("사용자가 작성한 일기가 PK로 삭제될 수 있다.")
    void deleteDiary() throws Exception {
        //given
        doNothing().when(diaryService).deleteDiary(any(UserDetail.class), any(Long.class));

        //when
        ResultActions perform =
                mockMvc.perform(
                        delete("/diaries/{diaryId}", 1L));
        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("delete diary",
                        getDocumentRequest(),
                        getDocumentResponse()));
    }

    @Test
    @DisplayName("사용자가 작성한 일기를 달별로 조회할 수 있다.")
    void getMonthlyDiary() throws Exception {
        // given
        String diaryDate = "2023-09";
        List<DiaryRes> expectedDiaries = DiaryControllerFixture.MONTHLY_DIARIES();
        MonthlyDiaryRes monthlyDiaryRes = MonthlyDiaryRes.builder()
                .monthlyDiaryRes(expectedDiaries)
                .build();
        given(diaryService.findMonthlyDiaryByDate(any(UserDetail.class), eq(diaryDate))).willReturn(monthlyDiaryRes);

        // when
        ResultActions perform = mockMvc.perform(
                get("/diaries")
                        .param("diary_date", diaryDate)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get monthlyDiaries",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("code").description("상태 코드"),
                                fieldWithPath("message").description("응답 메시지"),
                                fieldWithPath("data").description("응답 데이터").type(JsonFieldType.OBJECT).optional(),
                                fieldWithPath("data.monthly_diary_info").description("월별 일기 정보").type(JsonFieldType.ARRAY).optional(),
                                fieldWithPath("data.monthly_diary_info[].diary_id").type(JsonFieldType.NUMBER).description("일기 ID").optional(),
                                fieldWithPath("data.monthly_diary_info[].title").type(JsonFieldType.STRING).description("일기 제목").optional(),
                                fieldWithPath("data.monthly_diary_info[].weather").type(JsonFieldType.STRING).description("일기 날씨").optional(),
                                fieldWithPath("data.monthly_diary_info[].emoji").type(JsonFieldType.STRING).description("이모지").optional(),
                                fieldWithPath("data.monthly_diary_info[].contents").type(JsonFieldType.STRING).description("일기 내용").optional(),
                                fieldWithPath("data.monthly_diary_info[].diary_date").type(JsonFieldType.STRING).description("일기 날짜").optional(),
                                fieldWithPath("data.monthly_diary_info[].drawing_url").type(JsonFieldType.STRING).description("일기 그림 URL").optional())
                ));
    }

}