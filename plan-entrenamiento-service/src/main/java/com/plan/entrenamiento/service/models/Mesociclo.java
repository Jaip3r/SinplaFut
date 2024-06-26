package com.plan.entrenamiento.service.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "mesociclo")
public class Mesociclo {

    @Id
    @SequenceGenerator(
        name = "mesociclo_sequence",
        sequenceName = "mesociclo_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "mesociclo_sequence"
    )
    private Long id;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(value = EnumType.STRING)
    private TipoMesociclo tipo;

    @ManyToOne(targetEntity = PlanEntrenamiento.class)
    @JoinColumn(name = "plan_id")
    private PlanEntrenamiento planEntrenamiento;

    @OneToMany(targetEntity = Microciclo.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mesociclo_id", referencedColumnName = "id")
    private List<Microciclo> microciclos;
    
}
