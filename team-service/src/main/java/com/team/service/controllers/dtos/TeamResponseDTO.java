package com.team.service.controllers.dtos;

import lombok.Builder;

@Builder
public record TeamResponseDTO (

    Long id,

    String nombre,

    String telefono,

    String categoria,

    String escudo,

    Long clubId

){
    
}
