package com.jugador.service.controllers.dtos;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VincularJugadorDTO(

    @NotBlank(message = "El email del jugador es obligatorio")
    @Email(message = "Favor proporcionar una dirección de correo válida")
    String email,

    @NotNull(message = "El identificador del equipo es obligatorio")
    @Positive(message = "El identificador del equipo no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del equipo debe ser un número entero positivo")
    Long equipoId

) {
    
}
