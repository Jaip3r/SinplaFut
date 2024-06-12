package com.jugador.service.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jugador.service.exception.validator.ValidBirthDateValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = ValidBirthDateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ValidBirthDate {
    
    String message() default "Fecha de nacimiento no válida";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
