package com.jugador.service.exception.validator;

import java.util.List;

import com.jugador.service.exception.annotation.ValidEstado;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEstadoValidator implements ConstraintValidator<ValidEstado, String>{

    private List<String> validEstados = List.of("LESIONADO", "ACTIVO", "INACTIVO", "RETIRADO");

    @Override
    public boolean isValid(String estado, ConstraintValidatorContext context) {
        
        if (estado == null || estado.isBlank()){
            return false;
        }

        if (!validEstados.contains(estado.toUpperCase())){
            return false;
        }

        return true;

    }
    
}
