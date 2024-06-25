package com.sesion.entrenamiento.service.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sesion.entrenamiento.service.exception.validator.ValidTipoSesionValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidTipoSesionValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ValidTipoSesion {

    String message() default "Tipo de sesión de entrenamiento no válido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
}
