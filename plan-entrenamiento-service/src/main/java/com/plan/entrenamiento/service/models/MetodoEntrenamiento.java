package com.plan.entrenamiento.service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "metodo_entrenamiento")
public class MetodoEntrenamiento {

    @Id
    @SequenceGenerator(
        name = "metodo_entrenamiento_sequence",
        sequenceName = "metodo_entrenamiento_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "metodo_entrenamiento_sequence"
    )
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(value = EnumType.STRING)
    private TipoCarga carga;

    @Enumerated(value = EnumType.STRING)
    private TipoIntensidad intensidad;

    @Column(nullable = false)
    private int duracion;
    
}
