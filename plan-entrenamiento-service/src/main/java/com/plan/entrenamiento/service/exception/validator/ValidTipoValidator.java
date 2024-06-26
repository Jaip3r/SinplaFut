package com.plan.entrenamiento.service.exception.validator;

import java.util.List;

import com.plan.entrenamiento.service.exception.annotation.ValidTipo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTipoValidator implements ConstraintValidator<ValidTipo, String> {

    private List<String> validTiposMesociclos = List.of("PREPARACION_BASICA", "PREPARACION_BASICA_ESPECIFICA", "PREPARACION_MIXTA", "PREPARACION_ESPECIAL", "COMPETITIVO");

    @Override
    public boolean isValid(String tipo, ConstraintValidatorContext context) {
        
        if (tipo == null || tipo.isBlank()){
            return false;
        }

        if (!validTiposMesociclos.contains(tipo.toUpperCase())){
            return false;
        }

        return true;

    }
    
}
