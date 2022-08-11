package com.blog.blog.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AmazonS3ServiceImpl implements AmazonS3Service {
    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public PutObjectResult upload(String path, String fileName, Optional<Map<String, String>> optionalMetaData,
            InputStream inputStream) {
        // TODO Auto-generated method stub
        ObjectMetadata objectMetadata = new ObjectMetadata();

        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        log.debug("Path: " + path + ", FileName:" + fileName);

        PutObjectResult result = amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        amazonS3.setObjectAcl(path, fileName, CannedAccessControlList.PublicRead);
        return result;

    }

}
