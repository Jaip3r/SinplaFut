package com.plan.entrenamiento.service.controllers.dtos;

import java.util.List;

import com.plan.entrenamiento.service.models.MetodoEntrenamiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SesionEntrenamientoResponseDTO {
    
    private Long id;

    private String nombre;

    private String descripcion;

    private String fecha_inicio;

    private String hora;

    private int duracion;

    private String tipo_sesion;

    private Long equipoId;

    List<MetodoEntrenamiento> metodos;

}
