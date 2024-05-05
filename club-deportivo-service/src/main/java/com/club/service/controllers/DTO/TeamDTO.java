package com.club.service.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDTO {

    private Long id;

    private String nombre;

    private String telefono;

    private String categoria;

    private String escudo;
    
}
