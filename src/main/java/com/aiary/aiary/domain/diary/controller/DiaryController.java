package com.aiary.aiary.domain.diary.controller;


import com.aiary.aiary.domain.diary.dto.request.DiaryCreateRequest;
import com.aiary.aiary.domain.diary.service.DiaryService;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<ResultResponse> createDiary(@Valid @RequestBody DiaryCreateRequest createRequest){
        diaryService.createDiary(createRequest);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_CREATE_SUCCESS));
    }
    
    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ResultResponse> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_DELETE_SUCCESS));
    }

    @GetMapping
    public ResponseEntity<ResultResponse> findMonthlyDiary(@RequestParam("user_id") Long userId,
                                                           @RequestParam("diary_date") Date diaryDate){
        return ResponseEntity.ok(ResultResponse
                .of(ResultCode.DIARY_READ_SUCCESS, diaryService.findMonthlyDiary(userId, diaryDate)));
    }
}
