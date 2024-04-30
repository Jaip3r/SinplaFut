package com.club.service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "club_deportivo")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false
    )
    private String nombre;

    @Column(
        nullable = false
    )
    private String direccion;

    @Column(
        nullable = false
    )
    private String telefono;

    @Column(
        nullable = false
    )
    private String ciudad;

    @Column(
        nullable = false
    )
    private String pais;

    @Column(
        nullable = false,
        unique = true
    )
    private String estadio;


    // Columnas para el manejo de la imagen

    private String logoUrl;

    private String logoId;
    
}
