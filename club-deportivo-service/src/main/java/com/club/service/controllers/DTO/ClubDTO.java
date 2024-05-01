package com.club.service.controllers.DTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClubDTO (

    @NotBlank(message = "El nombre del club es obligatorio")
    @Length(min = 3, max = 20, message = "El nombre del club debe contener entre 3 y 20 caracteres")
    @Pattern(regexp = "^(?! )[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$", message = "El nombre del club no debe contener caracteres especiales")
    String nombre,

    @NotBlank(message = "La dirección del club es obligatoria")
    @Length(min = 3, max = 25, message = "La dirección del club debe contener entre 3 y 25 caracteres")
    String direccion,

    @NotBlank(message = "El telefono del club es obligatorio")
    @Length(min = 10, message = "El telefono del club debe contener 10 numeros")
    @Pattern(regexp = "^[1-9]+$", message = "El telefono debe contener solo numeros")
    String telefono,

    @NotBlank(message = "La ciudad de residencia del club es obligatoria")
    @Length(min = 3, max = 25, message = "El nombre de la ciudad debe contener entre 3 y 25 caracteres")
    String ciudad,

    @NotBlank(message = "El pais de residencia del club es obligatorio")
    @Length(min = 3, max = 25, message = "El nombre del pais debe contener entre 3 y 25 caracteres")
    String pais,

    @NotBlank(message = "El estadio del club es obligatorio")
    @Length(min = 3, max = 25, message = "El nombre del estadio debe contener entre 3 y 25 caracteres")
    @Pattern(regexp = "^(?! )[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$", message = "El nombre del estadio no debe contener caracteres especiales")
    String estadio

) {
    
}
