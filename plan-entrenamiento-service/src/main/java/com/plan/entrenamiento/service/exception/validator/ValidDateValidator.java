package com.plan.entrenamiento.service.exception.validator;

import java.time.LocalDate;

import com.plan.entrenamiento.service.exception.annotation.ValidDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate fecha, ConstraintValidatorContext context) {
        
        if (fecha == null){
            return false;
        }

        if (fecha.isBefore(LocalDate.now())) {
            return false;
        }

        return true;

    }
    
}
