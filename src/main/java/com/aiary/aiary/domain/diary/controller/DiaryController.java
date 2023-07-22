package com.aiary.aiary.domain.diary.controller;

import com.aiary.aiary.domain.diary.service.DiaryService;
import com.aiary.aiary.global.response.ResultCode;
import com.aiary.aiary.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v2/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<ResultResponse> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DIARY_DELETE_SUCCESS));
    }
}
