package com.jugador.service.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JugadorLesionResponseDTO {

    private Long id;

    private Long lesionId;

    private String lesion;

    private String fecha_inicio;

    private String fecha_fin;
    
}
