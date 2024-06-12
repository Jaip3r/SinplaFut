package com.jugador.service.persistence;

import java.util.Optional;

import com.jugador.service.models.JugadorLesion;

public interface IJugadorLesionDAO {

    Optional<JugadorLesion> findRecordByJugadorIdAndLesionId(Long jugador_id, Long lesion_id);

    void save(JugadorLesion jugadorLesion);

    void deleteById(Long id);
    
}
