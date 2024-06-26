package com.plan.entrenamiento.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlanEntrenamientoResponseDTO {

    private Long id;

    private String nombre;

    private String fechaInicio;

    private String fechaFin;

    private String descripcion;

    private String observaciones;

    private Long equipoId; 
    
}
