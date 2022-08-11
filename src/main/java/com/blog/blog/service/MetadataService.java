package com.blog.blog.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface MetadataService {
    public String upload(MultipartFile file, String fileName) throws IOException;
}
