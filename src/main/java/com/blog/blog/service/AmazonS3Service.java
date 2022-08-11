package com.blog.blog.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import com.amazonaws.services.s3.model.PutObjectResult;

public interface AmazonS3Service {
    public PutObjectResult upload(
            String path,
            String fileName,
            Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream);
}
