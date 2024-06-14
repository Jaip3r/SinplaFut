package com.club.service.controllers.DTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClubDTO (

    @NotBlank(message = "El nombre del club es obligatorio")
    @Length(min = 3, max = 30, message = "El nombre del club debe contener entre 3 y 30 carácteres")
    @Pattern(regexp = "^[^\\s][a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]*[^\\s]$", message = "El nombre del club no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "La dirección del club es obligatoria")
    @Length(min = 3, max = 25, message = "La dirección del club debe contener entre 3 y 25 carácteres")
    String direccion,

    @NotBlank(message = "El teléfono del club es obligatorio")
    @Length(min = 7, message = "El teléfono del club debe contener 7 números")
    @Pattern(regexp = "^[0-9]+$", message = "El telefono debe contener solo números")
    String telefono,

    @NotBlank(message = "La ciudad de residencia del club es obligatoria")
    @Length(min = 3, max = 25, message = "El nombre de la ciudad debe contener entre 3 y 25 carácteres")
    String ciudad,

    @NotBlank(message = "El país de residencia del club es obligatorio")
    @Length(min = 3, max = 25, message = "El nombre del país debe contener entre 3 y 25 carácteres")
    String pais,

    @NotBlank(message = "El estadio del club es obligatorio")
    @Length(min = 3, max = 25, message = "El nombre del estadio debe contener entre 3 y 25 carácteres")
    @Pattern(regexp = "^(?! )[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$", message = "El nombre del estadio no debe contener carácteres especiales")
    String estadio

) {
    
}
