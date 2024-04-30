package com.club.service.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClubResponseDTO {

    private Long id;

    private String nombre;

    private String direccion;

    private String telefono;

    private String ciudad;

    private String pais;

    private String estadio;

    private String logoUrl;
    
}
