package com.team.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlanEntrenamientoDTO {
    private Long id;

    private String nombre;

    private String descripcion;

    private String fecha_inicio;

    private String hora;

    private int duracion;

    private String tipo_sesion;

    private Long equipoId;

}
