package com.plan.entrenamiento.service.controllers.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plan.entrenamiento.service.exception.annotation.ValidTipo;

public record MesocicloDTO(

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaInicio,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fechaFin,

    @ValidTipo(message = "Tipo de mesociclo no permitido")
    String tipo

) {
    
}
