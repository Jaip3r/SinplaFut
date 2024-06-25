package com.jugador.service.persistence.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jugador.service.models.Jugador;
import com.jugador.service.models.JugadorLesion;
import com.jugador.service.models.Lesion;

public interface JugadorLesionRepository extends JpaRepository<JugadorLesion, Long> {

    // Query to find a record given player_id and injury_id
    @Query("SELECT jl FROM JugadorLesion jl WHERE jl.jugador = :jugador_id AND jl.lesion = :lesion_id")
    Optional<JugadorLesion> findByJugadorIdAndLesionId(Long jugador_id, Long lesion_id);

    // Buscar si el registro ya existe en la BD
    @Query("SELECT jl FROM JugadorLesion jl WHERE jl.jugador = ?1 AND jl.lesion = ?2 AND jl.fecha_inicio = ?3 AND jl.fecha_fin = ?4")
    Optional<JugadorLesion> findJugadorLesion(Jugador jugador, Lesion lesion, LocalDate fecha_inicio, LocalDate fecha_fin);
    
}
