package com.plan.entrenamiento.service.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {

    @Id
    @SequenceGenerator(
        name = "plan_entrenamiento_sequence",
        sequenceName = "plan_entrenamiento_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "plan_entrenamiento_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String observaciones;

    @Column(name = "equipo_id")
    private Long equipoId;

    @OneToMany(targetEntity = Mesociclo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private List<Mesociclo> mesociclos;
    
}
