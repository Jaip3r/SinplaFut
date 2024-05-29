package com.cuerpo.tecnico.service.exception.validator;

import java.time.LocalDate;
import java.time.Period;

import com.cuerpo.tecnico.service.exception.annotation.ValidDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDateValidator implements ConstraintValidator<ValidDate, LocalDate>{

    @Override
    public boolean isValid(LocalDate fecha, ConstraintValidatorContext context) {

        if (fecha == null){
            return false;
        }

        if (fecha.isAfter(LocalDate.now()) || fecha.isEqual(LocalDate.now())) {
            return false;
        }

        if (Period.between(fecha, LocalDate.now()).getYears() < 25){
            return false;
        }

        return true;
    }
    
}
