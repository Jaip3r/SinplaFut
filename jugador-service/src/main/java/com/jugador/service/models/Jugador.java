package com.jugador.service.models;

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
@Table(name = "jugador")
public class Jugador {

    @Id
    @SequenceGenerator(
        name = "jugador_sequence",
        sequenceName = "jugador_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "jugador_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    private LocalDate fecha_nacimiento;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String celular;

    @Enumerated(value = EnumType.STRING)
    private EstadoJugador estado;
    
    @Column(nullable = false)
    private int numero_camiseta;

    @Column(nullable = false)
    private String tipo_sangre;

    @Column(nullable = false)
    private float nivel_hemoglobina;

    @Column(nullable = false)
    private float consumo_o2;

    @Column(nullable = false)
    private float lactato_sangre;

    @Column(name = "equipo_id")
    private Long equipoId;

    @ManyToMany
    @JoinTable(
        name = "lesion_jugador",
        joinColumns = @JoinColumn(
            name = "jugador_id",
            referencedColumnName = "id" 
        ),
        inverseJoinColumns = @JoinColumn(
            name = "lesion_id",
            referencedColumnName = "id"
        )
    )
    private List<Lesion> lesiones;

}
