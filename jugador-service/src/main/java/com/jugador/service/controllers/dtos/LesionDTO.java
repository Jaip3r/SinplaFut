package com.jugador.service.controllers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LesionDTO(

    @NotBlank(message = "El nombre de la lesión es obligatorio")
    @Length(min = 10, max = 50, message = "El nombre de la lesión debe contener entre 10 y 50 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El nombre de la lesión no debe contener carácteres especiales")
    String nombre,

    // valid date
    LocalDate fecha_inicio,

    // valid date
    LocalDate fecha_fin,

    @NotBlank(message = "El tratamiento de la lesión es obligatorio")
    @Length(min = 20, max = 200, message = "La descripción de la lesión debe contener entre 20 y 200 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "La descripción de la lesión no debe contener carácteres especiales")
    String tratamiento,

    @NotBlank(message = "La observación de la lesión es obligatoria")
    @Length(min = 20, max = 210, message = "La observación de la lesión debe contener entre 20 y 210 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "La observación de la lesión no debe contener carácteres especiales")
    String observaciones

) {
    
}
