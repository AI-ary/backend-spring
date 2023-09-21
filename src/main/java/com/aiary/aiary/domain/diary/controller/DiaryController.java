package com.aiary.aiary.domain.diary.controller;


import com.aiary.aiary.domain.diary.dto.request.DiaryCreateReq;
import com.aiary.aiary.domain.diary.service.DiaryService;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import com.aiary.aiary.global.s3.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;


@Tag(name = "Diary", description = "일기 API")
@RestController
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/diaries")
public class DiaryController {

    private final DiaryService diaryService;
    private final S3UploadService s3UploadService;

    @Operation(summary = "일기 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> createDiary(@AuthenticationPrincipal UserDetail user,
                                                      @RequestPart("file") MultipartFile multipartFile,
                                                      @Valid @RequestPart(value="createRequest") DiaryCreateReq createRequest) throws IOException {
        String drawingUrl = s3UploadService.saveFile(multipartFile, user.getUsername());
        diaryService.createDiary(user, createRequest, drawingUrl);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_CREATE_SUCCESS));
    }

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ResultResponse> deleteDiary(@AuthenticationPrincipal UserDetail user,
                                                      @PathVariable Long diaryId){
        diaryService.deleteDiary(user, diaryId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_DELETE_SUCCESS));
    }

    @Operation(summary = "일기 월 별 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> getMonthlyDiary(@AuthenticationPrincipal UserDetail user,
                                                           @RequestParam("diary_date") String diaryDate){
        return ResponseEntity.ok(ResultResponse
                .of(ResultCode.DIARY_READ_SUCCESS, diaryService.findMonthlyDiaryByDate(user, diaryDate)));
    }

    @Operation(summary = "일기 제목/내용 검색")
    @GetMapping("/search")
    public ResponseEntity<ResultResponse> searchDiariesByKeyWord(@AuthenticationPrincipal UserDetail user,
                                                                 @RequestParam(defaultValue = "0", required = false) int page,
                                                                 @RequestParam(defaultValue = "40", required = false)int size,
                                                                 @RequestParam("diary_date") String diaryDate,
                                                                 @RequestParam String keyword){
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(ResultResponse
                .of(ResultCode.DIARY_READ_SUCCESS,
                        diaryService.searchDiariesByKeyword(user, pageRequest, diaryDate, keyword)));

    }
}
