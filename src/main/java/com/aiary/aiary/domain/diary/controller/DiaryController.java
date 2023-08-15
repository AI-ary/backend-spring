package com.aiary.aiary.domain.diary.controller;


import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.service.DiaryService;
import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@Tag(name = "Diary", description = "일기 API")
@Controller
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "일기 등록")
    @PostMapping
    public ResponseEntity<ResultResponse> createDiary(@AuthenticationPrincipal UserDetail user,  @Valid @RequestBody DiaryCreateRequest createRequest){
        diaryService.createDiary(user.getUser(), createRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_CREATE_SUCCESS));
    }

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ResultResponse> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
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
}
