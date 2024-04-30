package com.club.service.services;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    Map<String, String> uploadFile(MultipartFile multipartFile, String name);

    Map<String, String> updateFile(MultipartFile multipartFile, String public_id);

    Map<String, String> delete(String id);
    
}
