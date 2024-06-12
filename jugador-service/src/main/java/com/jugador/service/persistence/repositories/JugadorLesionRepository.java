package com.jugador.service.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jugador.service.models.JugadorLesion;

public interface JugadorLesionRepository extends JpaRepository<JugadorLesion, Long> {

    // Query to find a record given player_id and injury_id
    @Query("SELECT jl FROM JugadorLesion jl WHERE jl.jugador_id = :jugador_id AND jl.lesion_id = :lesion_id")
    Optional<JugadorLesion> findByJugadorIdAndLesionId(Long jugador_id, Long lesion_id);
    
}
