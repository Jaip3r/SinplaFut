package com.cuerpo.tecnico.service.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cuerpo.tecnico.service.exception.validator.ValidDateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ValidDate {

    String message() default "Fecha no válida";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
    
}
