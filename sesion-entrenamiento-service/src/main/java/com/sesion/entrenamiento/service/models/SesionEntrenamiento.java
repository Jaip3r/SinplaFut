package com.sesion.entrenamiento.service.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
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
@Table(name = "sesion_entrenamiento")
public class SesionEntrenamiento {

    @Id
    @SequenceGenerator(
        name = "sesion_entrenamiento_sequence",
        sequenceName = "sesion_entrenamiento_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "sesion_entrenamiento_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fecha_inicio;

    @Column(nullable = false)
    private String hora;

    @Column(nullable = false)
    private int duracion;

    @Enumerated(value = EnumType.STRING)
    private TipoSesionEntrenamiento tipo_sesion;

    @ManyToMany
    @JoinTable(
        name = "sesion_metodo",
        joinColumns = @JoinColumn(
            name = "sesion_id",
            referencedColumnName = "id" 
        ),
        inverseJoinColumns = @JoinColumn(
            name = "metodo_id",
            referencedColumnName = "id"
        )
    )
    private List<MetodoEntrenamiento> metodos;

    @Column(name = "equipo_id")
    private Long equipoId;
    
}
