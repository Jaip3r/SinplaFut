package com.plan.entrenamiento.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MesocicloResponseDTO {

    private Long id;

    private String fechaInicio;

    private String fechaFin;

    private String tipo;

    private Long plan_id;
    
}
