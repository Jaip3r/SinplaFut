package com.jugador.service.exception.validator;

import java.time.LocalDate;
import java.time.Period;

import com.jugador.service.exception.annotation.ValidBirthDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidBirthDateValidator implements ConstraintValidator<ValidBirthDate, LocalDate>{

    @Override
    public boolean isValid(LocalDate fecha, ConstraintValidatorContext context) {

        if (fecha == null){
            return false;
        }

        if (fecha.isAfter(LocalDate.now()) || fecha.isEqual(LocalDate.now())) {
            return false;
        }

        if (Period.between(fecha, LocalDate.now()).getYears() < 16 || Period.between(fecha, LocalDate.now()).getYears() > 55){
            return false;
        }

        return true;
    }
    
}
