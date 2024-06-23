package com.jugador.service.controllers.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JugadorLesionDTO(

    @NotNull(message = "El identificador de la lesión es obligatorio")
    @Positive(message = "El identificador de la lesión no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador de la lesión debe ser un número entero positivo")
    Long lesionId,
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fecha_inicio,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fecha_fin

) {
    
}
