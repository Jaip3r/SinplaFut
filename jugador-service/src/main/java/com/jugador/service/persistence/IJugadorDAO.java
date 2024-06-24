package com.jugador.service.persistence;

import java.util.List;
import java.util.Optional;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;

public interface IJugadorDAO {

    List<Jugador> findAll();

    List<Jugador> findByEstado(EstadoJugador estado, Long equipoId);

    List<Jugador> findByEquipo(Long equipo_id);

    Optional<Jugador> findById(Long id);

    Optional<Jugador> findByDocumento(String documento);

    Optional<Jugador> findByEmail(String email);

    void save(Jugador jugador);

    void deleteById(Long id);
    
}
