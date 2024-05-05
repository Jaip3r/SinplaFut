package com.cuerpo.tecnico.service.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cuerpo_tecnico")
public class CuerpoTecnico {
    
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
    private String apellido;

    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate fecha_nacimiento;

    @Column(
        nullable = false,
        unique = true
    )
    private String email;

    @Column(
        nullable = false,
        unique = true
    )
    private String documento;

    @Column(
        nullable = false
    )
    private String telefono;

    @Enumerated(value = EnumType.STRING)
    private CuerpoTecnicoType tipo;

}
