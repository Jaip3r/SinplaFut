package com.cuerpo.tecnico.service.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cuerpo.tecnico.service.exception.validator.ValidTipoValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidTipoValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ValidTipo {

     String message() default "Tipo de integrante de cuerpo técnico no válido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
}
