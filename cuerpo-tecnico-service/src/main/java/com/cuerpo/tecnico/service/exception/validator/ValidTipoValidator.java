package com.cuerpo.tecnico.service.exception.validator;

import java.util.List;

import com.cuerpo.tecnico.service.exception.annotation.ValidTipo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidTipoValidator implements ConstraintValidator<ValidTipo, String> {

    private List<String> validTipos = List.of("ENTRENADOR", "PREPARADOR_FISICO", "MEDICO", "DIRECTOR_TECNICO", "ASISTENTE_TECNICO");

    @Override
    public boolean isValid(String tipo, ConstraintValidatorContext context) {
        
        if (tipo == null || tipo.isBlank()){
            return false;
        }

        if (!validTipos.contains(tipo.toUpperCase())){
            return false;
        }

        return true;

    }
    
}
