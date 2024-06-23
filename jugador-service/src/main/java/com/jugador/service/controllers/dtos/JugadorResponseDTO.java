package com.jugador.service.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JugadorResponseDTO {

    private Long id;

    private String nombre;

    private String apellido;

    private String fecha_nacimiento;

    private String documento;

    private String email;

    private String direccion;

    private String celular;

    private String estado;

    private int numero_camiseta;

    private String tipo_sangre;

    private float nivel_hemoglobina;

    private float consumo_o2;

    private float lactato_sangre;

    private Long equipoId;
    
}
