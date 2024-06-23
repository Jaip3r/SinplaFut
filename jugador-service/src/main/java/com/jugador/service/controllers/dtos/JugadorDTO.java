package com.jugador.service.controllers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jugador.service.exception.annotation.ValidBirthDate;
import com.jugador.service.exception.annotation.ValidEstado;
import com.jugador.service.exception.annotation.ValidTipoSangre;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record JugadorDTO(

    @NotBlank(message = "El nombre del jugador es obligatorio")
    @Length(min = 2, max = 40, message = "El nombre del jugador debe contener entre 2 y 40 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El nombre del jugador no debe contener carácteres especiales")
    String nombre,

    @NotBlank(message = "El apellido del jugador es obligatorio")
    @Length(min = 2, max = 45, message = "El apellido del jugador debe contener entre 2 y 45 carácteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+( [a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)*$", message = "El apellido del jugador no debe contener carácteres especiales")
    String apellido,

    @ValidBirthDate(message = "El jugador debe contar con una edad mayor a 16 y menor a 55")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    LocalDate fecha_nacimiento,

    @NotBlank(message = "El documento del jugador del es obligatorio")
    @Length(min = 10, message = "El documento del jugador debe contener 10 digitos")
    @Pattern(regexp = "^[0-9]+$", message = "El documento del jugador debe contener solo números")
    String documento,

    @NotBlank(message = "El email del jugador es obligatorio")
    @Email(message = "Favor proporcionar una dirección de correo válida")
    String email,

    @NotBlank(message = "La dirección de residencia del jugador es obligatoria")
    @Length(min = 10, max = 45, message = "La dirección del jugador debe contener entre 10 y 45 carácteres")
    String direccion,

    @NotBlank(message = "El número de celular del jugador es obligatorio")
    @Length(min = 10, message = "El número de celular del jugador debe contener 10 números")
    @Pattern(regexp = "^[0-9]+$", message = "El número de celular del jugador debe contener solo números")
    String celular,

    @ValidEstado(message = "Favor proporcionar un estado de jugador válido")
    String estado,

    @NotNull(message = "El número de la camiseta es obligatorio")
    @Positive(message = "El número de la camiseta no puede ser cero 0 negativo")
    @Digits(integer = 2, fraction = 0, message = "El número de la camiseta del jugador debe ser un número entero positivo")
    int numero_camiseta,

    @ValidTipoSangre(message = "Favor proporcionar un tipo de sangre válido")
    String tipo_sangre,

    @DecimalMin(value = "14.0", inclusive = false, message = "El nivel de hemoglobina es muy bajo")
    @DecimalMax(value = "18.0", inclusive = true, message = "Nivel de hemoglobina demasiado alto")
    @Digits(integer = 2, fraction = 2, message = "Favor solo manejar 2 digitos enteros y 2 posibles decimales")
    float nivel_hemoglobina,

    @DecimalMin(value = "30.0", inclusive = false, message = "Niveles de consumo de O2 muy bajos")
    @DecimalMax(value = "90.0", inclusive = true, message = "Nivel de consumo de O2 demasiado alto")
    @Digits(integer = 2, fraction = 2, message = "Favor solo manejar 2 digitos enteros y 2 posibles decimales")
    float consumo_o2,

    @DecimalMin(value = "1.0", inclusive = false, message = "El lactato en sangre es muy bajo")
    @DecimalMax(value = "5.0", inclusive = true, message = "Nivel de lactato en sangre demasiado alto")
    @Digits(integer = 1, fraction = 2, message = "Favor solo manejar 1 digitos enteros y 2 posibles decimales")
    float lactato_sangre,

    @NotNull(message = "El identificador del equipo es obligatorio")
    @Positive(message = "El identificador del equipo no puede ser cero 0 negativo")
    @Digits(integer = Integer.MAX_VALUE, fraction = 0, message = "El identificador del equipo debe ser un número entero positivo")
    Long equipoId

) {
    

}
