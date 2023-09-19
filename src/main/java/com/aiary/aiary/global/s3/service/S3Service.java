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
public class S3Service {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String saveFile(MultipartFile multipartFile, String category, String username) throws IOException {
        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        String directoryPath = "images/" + category + "/" + username + "/";
        String fileName = directoryPath + UUID.randomUUID() + "." + multipartFile.getOriginalFilename();

        amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    public void deleteFile(String imageUrl) {
        String s3Key = extractS3Key(imageUrl);

        if (s3Key != null) {
            amazonS3Client.deleteObject(bucket, s3Key);
        }
    }

    private String extractS3Key(String imageUrl) {
        try {
            // url 에 @ 대신 %40 으로 반환되어있지만, key 는 @ 로 되어있기 때문에 변환
            String url = imageUrl.replace("%40", "@");
            int splitIndex = url.indexOf(".com/");
            if (splitIndex != -1) {
                return url.substring(splitIndex + 5);  // ".com/" 다음 문자열부터가 객체 키
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
