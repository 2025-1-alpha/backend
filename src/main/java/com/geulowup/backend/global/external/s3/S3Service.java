package com.geulowup.backend.global.external.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.geulowup.backend.global.exception.ApiException;
import com.geulowup.backend.global.exception.GlobalErrorCode;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.base-url}")
    private String baseUrl;

    public String uploadFile(MultipartFile file, String newFileName) {
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String changedName = newFileName + ext;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try {
            amazonS3.putObject(
                    new PutObjectRequest(bucket, changedName, file.getInputStream(), metadata)
            );
        } catch (IOException e) {
            throw new ApiException(GlobalErrorCode.FILE_UPLOAD_FAILED, e);
        }

        return amazonS3.getUrl(bucket, changedName).toString();
    }
}