package com.team.service.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    Map<String, String> uploadFile(MultipartFile multipartFile, String public_id) throws IOException;

    Map<String, String> delete(String id) throws IOException;
    
}
