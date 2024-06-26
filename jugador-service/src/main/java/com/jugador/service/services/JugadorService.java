package com.jugador.service.services;

import java.util.List;
import java.util.Optional;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;

public interface JugadorService {

    List<Jugador> findAll();

    List<Jugador> findByEquipo(Long equipo_id);

    List<Jugador> findByEstado(EstadoJugador estado, Long equipoId);

    List<Jugador> findAllByEmailOrDocumento(String email, String documento);

    Optional<Jugador> findByEquipoAndCamiseta(Long equipoId, int numero_camiseta);

    Optional<Jugador> findById(Long id);

    void save(Jugador jugador);

    void deleteById(Long id);

}
