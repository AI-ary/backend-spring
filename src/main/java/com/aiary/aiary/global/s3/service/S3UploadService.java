package com.aiary.aiary.global.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile, String username) throws IOException {
        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String directoryPath = "images/result/" + username + "/";
        String fileName = directoryPath + UUID.randomUUID() + "." + multipartFile.getOriginalFilename();

        amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }
}
