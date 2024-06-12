package com.jugador.service.exception.validator;

import java.util.List;

import com.jugador.service.exception.annotation.ValidTipoSangre;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTipoSangreValidator implements ConstraintValidator<ValidTipoSangre, String>{

    private List<String> validEstados = List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");

    @Override
    public boolean isValid(String tipo, ConstraintValidatorContext context) {
        
        if (tipo == null || tipo.isBlank()){
            return false;
        }

        if (!validEstados.contains(tipo.toUpperCase())){
            return false;
        }

        return true;

    }
    
}
