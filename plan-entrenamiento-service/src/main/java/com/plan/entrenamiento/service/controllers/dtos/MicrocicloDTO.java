package com.plan.entrenamiento.service.controllers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record MicrocicloDTO(

    @NotBlank(message = "El nombre del microciclo es obligatorio")
    @Length(min = 10, max = 60, message = "El nombre del microciclo debe contener entre 10 y 60 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "El nombre del microciclo no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "La descripción del microciclo es obligatorio")
    @Length(min = 10, max = 150, message = "La descripción del microciclo debe contener entre 10 y 150 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "La descripción del microciclo no debe contener carácteres especiales")
    String descipcion,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaInicio,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaFin,

    @NotNull(message = "El identificador del mesociclo es obligatorio")
    @Positive(message = "El identificador del mesociclo no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del mesociclo debe ser un número entero positivo")
    Long mesociclo_id

) {
    
}
