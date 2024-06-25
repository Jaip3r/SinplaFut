package com.sesion.entrenamiento.service.controllers.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record MetodoEntrenamientoDTO(

    @NotBlank(message = "El nombre del método de entrenamiento es obligatorio")
    @Length(min = 2, max = 40, message = "El nombre del método debe contener entre 2 y 40 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El nombre del método no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "La descripción del método es obligatoria")
    @Length(min = 10, max = 255, message = "La descripción del método debe contener entre 10 y 230 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "La descripción no debe contener carácteres especiales")
    String descripcion,

    String carga,

    String intensidad,

    @NotNull(message = "La duración del método de entrenamiento es obligatoria")
    @Positive(message = "La duración del método de entrenamiento no puede ser cero o negativa")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "La duración del método debe ser un número entero positivo")
    int duracion

) {
    
}
