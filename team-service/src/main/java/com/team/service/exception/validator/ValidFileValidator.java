package com.team.service.exception.validator;

import org.springframework.web.multipart.MultipartFile;

import com.team.service.exception.annotation.ValidFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private static final long MAX_FILE_SIZE = 3 * 1024 * 1024;

    @SuppressWarnings("null")
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        
        if (file != null){

            if (file.isEmpty()) {
                return false;
            }
    
            if (file.getSize() > MAX_FILE_SIZE) {
                return false;
            }
    
            if (!file.getOriginalFilename().toLowerCase().endsWith(".png")){
                return false;
            }

        }

        return true;

    }
    
}
