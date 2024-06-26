package com.plan.entrenamiento.service.controllers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record PlanEntrenamientoDTO(

    @NotBlank(message = "El nombre del plan de entrenamiento es obligatorio")
    @Length(min = 10, max = 60, message = "El nombre del plan de entrenamiento debe contener entre 10 y 60 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "El nombre del plan de entrenamiento no debe contener carácteres especiales")
    String nombre,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaInicio,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaFin,

    @NotBlank(message = "La descripción del plan de entrenamiento es obligatorio")
    @Length(min = 10, max = 150, message = "La descripción del plan de entrenamiento debe contener entre 10 y 150 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "La descripción del plan de entrenamiento no debe contener carácteres especiales")
    String descripcion,

    @NotBlank(message = "Las observaciones del plan de entrenamiento son obligatorias")
    @Length(min = 10, max = 180, message = "Las observaciones del plan de entrenamiento deben contenern entre 10 y 180 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "Las observaciones del plan de entrenamiento no deben contener carácteres especiales")
    String observaciones,

    @NotNull(message = "El identificador del equipo es obligatorio")
    @Positive(message = "El identificador del equipo no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del equipo debe ser un número entero positivo")
    Long equipoId

) {
    
}
