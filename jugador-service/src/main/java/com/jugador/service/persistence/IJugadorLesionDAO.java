package com.jugador.service.persistence;

import java.time.LocalDate;
import java.util.Optional;

import com.jugador.service.models.Jugador;
import com.jugador.service.models.JugadorLesion;
import com.jugador.service.models.Lesion;

public interface IJugadorLesionDAO {

    Optional<JugadorLesion> findRecordByJugadorIdAndLesionId(Long jugador_id, Long lesion_id);

    Optional<JugadorLesion> findJugadorLesion(Jugador jugador, Lesion lesion, LocalDate fecha_inicio, LocalDate fecha_fin);

    Optional<JugadorLesion> findById(Long id);

    void save(JugadorLesion jugadorLesion);

    void deleteById(Long id);
    
}
