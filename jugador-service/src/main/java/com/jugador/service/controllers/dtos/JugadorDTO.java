package com.jugador.service.controllers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record JugadorDTO (

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Length(min = 2, max = 40, message = "El nombre del jugador debe contener entre 2 y 40 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El nombre del jugador no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "El apellido del jugador es obligatorio")
    @Length(min = 2, max = 45, message = "El apellido del jugador debe contener entre 2 y 45 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El apellido del jugador no debe contener carácteres especiales")
    String apellido,

    //@ValidDate(message = "El jugador debe contar con una edad mayor o igual a 16")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fecha_nacimiento,

    @NotBlank(message = "El teléfono del jugador es obligatorio")
    @Length(min = 7, message = "El telefono del jugador debe contener 7 números")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono del jugador debe contener solo numeros")
    String telefono,

    @NotBlank(message = "El teléfono del jugador es obligatorio")
    @Length(min = 7, message = "El telefono del jugador debe contener 7 números")
    String direccion,

    @NotBlank(message = "El teléfono del jugador es obligatorio")
    @Length(min = 7, message = "El telefono del jugador debe contener 7 números")
    @Pattern(regexp = "^[0-9]+$", message = "El teléfono del jugador debe contener solo numeros")
    String celular,

    String estado,

    int numero_camiseta,

    String camiseta,

    String medias,

    String zapatos,

    String pantaloneta,

    String tipo_sangre,

    float nivel_hemoglobina,

    float consumo_o2,

    float lactato_sangre,

    Long equipoId

) {
    
}
