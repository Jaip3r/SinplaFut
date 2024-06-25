package com.sesion.entrenamiento.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MetodoEntrenamientoResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String carga;

    private String intensidad;

    private int duracion;
    
}
