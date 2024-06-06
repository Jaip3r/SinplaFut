package com.plan.entrenamiento.service.controllers.dtos;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.plan.entrenamiento.service.exception.annotation.ValidDate;
import com.plan.entrenamiento.service.exception.annotation.ValidTipoSesion;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record SesionEntrenamientoDTO(

    @NotBlank(message = "El nombre de la sesión de entrenamiento es obligatorio")
    @Length(min = 2, max = 40, message = "El nombre de la sesión debe contener entre 2 y 40 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El nombre de la sesión no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "La descripción de la sesión es obligatoria")
    @Length(min = 10, max = 255, message = "La descripción de la sesión debe contener entre 10 y 230 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "La descripción no debe contener carácteres especiales")
    String descripcion,

    @ValidDate(message = "Favor especificar una fecha de inicio válida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fecha_inicio,

    @NotBlank(message = "La hora de inicio de la sesión de entrenamiento es obligatoria")
    String hora,

    @NotNull(message = "La duración de la sesión de entrenamiento es obligatoria")
    @Positive(message = "La duración de la sesión de entrenamiento no puede ser cero o negativa")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "La duración se la sesión debe ser un número entero positivo")
    int duracion,

    @ValidTipoSesion(message = "Favor proporcionar un tipo de sesión de entrenamiento válido")
    String tipo,

    @NotNull(message = "El identificador del equipo es obligatorio")
    @Positive(message = "El identificador del equipo no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del equipo debe ser un número entero positivo")
    Long equipoId,

    @NotNull(message = "Los métodos de entrenamiento no pueden ser nulos")
    List<MetodoEntrenamientoResponseDTO> metodos

) {

    
}
