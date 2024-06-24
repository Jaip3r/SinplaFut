package com.jugador.service.services;

import java.util.List;
import java.util.Optional;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;

public interface JugadorService {

    List<Jugador> findAll();

    List<Jugador> findByEquipo(Long equipo_id);

    List<Jugador> findByEstado(EstadoJugador estado, Long equipoId);

    Optional<Jugador> findByDocumento(String documento);

    Optional<Jugador> findById(Long id);

    Optional<Jugador> findByEmail(String email);

    void save(Jugador jugador);

    void deleteById(Long id);
    
}
