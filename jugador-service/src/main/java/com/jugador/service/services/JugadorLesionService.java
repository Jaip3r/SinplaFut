package com.jugador.service.services;

import java.util.Optional;

import com.jugador.service.models.JugadorLesion;

public interface JugadorLesionService {

    Optional<JugadorLesion> findRecordByJugadorIdAndLesionId(Long jugador_id, Long lesion_id);

    void save(JugadorLesion jugadorLesion);

    void deleteById(Long id);
    
}
