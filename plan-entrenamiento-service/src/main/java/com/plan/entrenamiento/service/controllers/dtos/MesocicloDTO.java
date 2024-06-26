package com.plan.entrenamiento.service.controllers.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plan.entrenamiento.service.exception.annotation.ValidTipo;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MesocicloDTO(

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaInicio,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaFin,

    @ValidTipo(message = "Tipo de mesociclo no permitido")
    String tipo,

    @NotNull(message = "El identificador del plan de entrenamiento es obligatorio")
    @Positive(message = "El identificador del plan de entrenamiento no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del plan de entrenamiento debe ser un número entero positivo")
    Long plan_id

) {
    
}
