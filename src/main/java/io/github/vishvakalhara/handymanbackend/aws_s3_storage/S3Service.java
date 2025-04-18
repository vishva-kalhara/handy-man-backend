package io.github.vishvakalhara.handymanbackend.aws_s3_storage;

import io.github.vishvakalhara.handymanbackend.error_handling.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private S3Client s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file, ResourceType resourceType) {

        String prefix = resourceType == ResourceType.TASK_IMAGE ? "task/" : "user/";
        String fileName = prefix + file.getOriginalFilename() + "_" + UUID.randomUUID();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        try {
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }

        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }
}
