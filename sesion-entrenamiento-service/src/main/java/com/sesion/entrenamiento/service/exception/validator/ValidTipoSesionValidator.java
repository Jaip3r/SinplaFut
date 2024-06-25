package com.sesion.entrenamiento.service.exception.validator;

import java.util.List;

import com.sesion.entrenamiento.service.exception.annotation.ValidTipoSesion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTipoSesionValidator implements ConstraintValidator<ValidTipoSesion, String> {

    private List<String> validTipoSesion = List.of("APRENDIZAJE_TECNICO", "ACONDICIONAMIENTO_FISICO", "CONTROL", "DESARROLLO", "RECUPERACION", "MIXTO");

    @Override
    public boolean isValid(String tipo, ConstraintValidatorContext context) {
        
        if (tipo == null || tipo.isBlank()){
            return false;
        }

        if (!validTipoSesion.contains(tipo.toUpperCase())){
            return false;
        }

        return true;

    }
    
}
