package com.plan.entrenamiento.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MicrocicloResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String fechaInicio;

    private String fechaFin;

    private Long mesociclo_id;
    
}
