package com.aiary.aiary.global.s3.controller;

import com.aiary.aiary.domain.user.entity.UserDetail;
import com.aiary.aiary.global.result.ResultCode;
import com.aiary.aiary.global.result.ResultResponse;
import com.aiary.aiary.global.s3.service.S3UploadService;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@RequestMapping("api/s3")
public class S3Controller {
    private final AmazonS3Client amazonS3Client;
    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<ResultResponse> uploadFile(@AuthenticationPrincipal UserDetail user,
                                                     @RequestParam("file") MultipartFile multipartFile) throws IOException {
        String drawingUrl = s3UploadService.saveFile(multipartFile, user.getUsername());

        return ResponseEntity.ok(ResultResponse.of(ResultCode.S3_UPLOAD_SUCCESS, drawingUrl));
    }
}
