package com.team.service.controllers.dtos;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record TeamDTO(

    @NotBlank(message = "El nombre del equipo es obligatorio")
    @Length(min = 3, max = 30, message = "El nombre del equipo debe contener entre 3 y 20 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9]+)*$", message = "El nombre del equipo no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "El teléfono del equipo es obligatorio")
    @Length(min = 7, message = "El teléfono del equipo debe contener 7 números")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono del equipo debe contener solo números")
    String telefono,

    @NotBlank(message = "La categoria del equipo es obligatoria")
    String categoria,

    @NotNull(message = "El identificador del club es obligatorio")
    @Positive(message = "El identificador del club no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del club debe ser un número entero positivo")
    Long clubId

) {
    
}
