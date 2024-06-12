package com.jugador.service.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "jugador_lesion")
public class JugadorLesion {
    
    @Id
    @SequenceGenerator(
        name = "jugador_lesion_sequence",
        sequenceName = "jugador_lesion_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "jugador_lesion_sequence"
    )
    private Long id;

    @ManyToOne(targetEntity = Jugador.class)
    @JoinColumn(name = "jugador_id")
    private Jugador jugador;

    @ManyToOne(targetEntity = Lesion.class)
    @JoinColumn(name = "lesion_id")
    private Lesion lesion;

    private LocalDate fecha_inicio;

    private LocalDate fecha_fin;

}
