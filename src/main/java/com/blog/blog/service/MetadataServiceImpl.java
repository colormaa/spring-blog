package com.blog.blog.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;

@Service
public class MetadataServiceImpl implements MetadataService {
    @Autowired
    private AmazonS3Service amazonS3Service;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public String upload(MultipartFile file, String fileName) throws IOException {
        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        String UID = UUID.randomUUID().toString();
        String path = String.format("%s/%s", bucketName, UID);
        // Uploading file to s3
        PutObjectResult putObjectResult = amazonS3Service.upload(
                path, fileName, Optional.of(metadata), file.getInputStream());
        // https://bolormaa-bucket-test.s3.amazonaws.com/d26e76d5-b1b1-42d0-bb2b-6f58e6359a4a/aaa.jpeg
        return "https://" + bucketName + ".s3.amazonaws.com/" + UID + "/" + fileName;
    }

}
