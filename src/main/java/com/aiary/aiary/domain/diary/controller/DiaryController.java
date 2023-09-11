package com.aiary.aiary.domain.diary.controller;


import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.dto.request.DiaryUpdateRequest;
import com.aiary.aiary.domain.diary.service.DiaryService;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import com.aiary.aiary.global.s3.service.S3UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;

@Tag(name = "Diary", description = "일기 API")
@Controller
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;
    private final S3UploadService s3UploadService;

    @Operation(summary = "일기 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> createDiary(@AuthenticationPrincipal UserDetail user,
                                                      @RequestPart("file") MultipartFile multipartFile,
                                                      @Valid @RequestPart(value="createRequest") DiaryCreateRequest createRequest) throws IOException {
        String drawingUrl = s3UploadService.saveFile(multipartFile, user.getUsername());
        diaryService.createDiary(user.getUser(), createRequest, drawingUrl);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_CREATE_SUCCESS));
    }

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ResultResponse> deleteDiary(@AuthenticationPrincipal UserDetail user, @PathVariable Long diaryId){
        diaryService.deleteDiary(user, diaryId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_DELETE_SUCCESS));
    }

    @Operation(summary = "일기 월 별 조회")
    @GetMapping
    public ResponseEntity<ResultResponse> findMonthlyDiary(@AuthenticationPrincipal UserDetail user,
                                                           @RequestParam("diary_date")
                                                           @DateTimeFormat(pattern = "yyyy-MM") Date diaryDate){
        return ResponseEntity.ok(ResultResponse
                .of(ResultCode.DIARY_READ_SUCCESS, diaryService.findMonthlyDiary(user.getUser().getId(), diaryDate)));
    }

    @Operation(summary = "일기 수정")
    @PutMapping("/{diaryId}")
    public ResponseEntity<ResultResponse> updateDiary(@AuthenticationPrincipal UserDetail user,
                                                      @RequestPart("file") MultipartFile multipartFile,
                                                      @PathVariable Long diaryId,
                                                      @Valid @RequestPart(value="updateRequest") DiaryUpdateRequest updateRequest) throws IOException {
        String drawingUrl = s3UploadService.saveFile(multipartFile, user.getUsername());
        diaryService.updateDiary(updateRequest, diaryId, drawingUrl);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_UPDATE_SUCCESS));
    }
}
