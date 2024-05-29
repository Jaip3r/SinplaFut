package com.cuerpo.tecnico.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CuerpoTecnicoResponseDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String fecha_nacimiento;

    private String email;

    private String documento;

    private String telefono;

    private String tipo;

    private Long equipo_id;
    
}
