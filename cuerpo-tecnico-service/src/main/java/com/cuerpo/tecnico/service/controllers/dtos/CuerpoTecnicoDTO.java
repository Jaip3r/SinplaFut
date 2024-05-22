package com.cuerpo.tecnico.service.controllers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CuerpoTecnicoDTO(

    @NotBlank(message = "El nombre del integrante del cuerpo tecnico es obligatorio")
    @Length(min = 2, max = 40, message = "El nombre del integrante debe contener entre 2 y 40 caracteres")
    @Pattern(regexp = "^(?! )[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$", message = "El nombre del integrante no debe contener caracteres especiales")
    String nombre,

    @NotBlank(message = "El apellido del integrante del cuerpo tecnico es obligatorio")
    @Length(min = 2, max = 45, message = "El apellido del integrante debe contener entre 2 y 45 caracteres")
    @Pattern(regexp = "^(?! )[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$", message = "El apellido del integrante no debe contener caracteres especiales")
    String apellido,

    @NotBlank(message = "El email del integrante del cuerpo tecnico es obligatorio")
    @Email(message = "Favor proporcionar una dirección de correo valida")
    String email,

    @NotBlank(message = "El documento del integrante del cuerpo tecnico es obligatorio")
    @Length(min = 10, message = "El documento del integrante debe contener 10 numeros")
    @Pattern(regexp = "^[1-9]+$", message = "El documento del integrante debe contener solo numeros")
    String documento,

    
    LocalDate fecha_nacimiento,

    @NotBlank(message = "El telefono del integrante del cuerpo tecnico es obligatorio")
    @Length(min = 10, message = "El telefono del integrante debe contener 10 numeros")
    @Pattern(regexp = "^[1-9]+$", message = "El telefono del integrante debe contener solo numeros")
    String telefono,

    @NotNull(message = "El identificador del equipo es obligatorio")
    @Positive(message = "El identificador del equipo no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del equipo debe ser un número entero positivo")
    Long equipoId,

    @NotBlank(message = "El tipo de integrante de cuerpo tecnico es obligatorio")
    String tipo

) {
    
}
