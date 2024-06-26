package com.jugador.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LesionResponseDTO {

    private Long id;

    private String nombre;

    private String tratamiento;

    private String observaciones;
    
}
