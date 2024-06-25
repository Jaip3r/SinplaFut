package com.jugador.service.services;

import java.util.List;
import java.util.Optional;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;

public interface JugadorService {

    List<Jugador> findAll();

    List<Jugador> findByEquipo(Long equipo_id);

    List<Jugador> findByEstado(EstadoJugador estado, Long equipoId);

    List<Jugador> findAllByEmailOrDocumentoOrCamiseta(String email, String documento, int numero_camiseta);

    Optional<Jugador> findById(Long id);

    void save(Jugador jugador);

    void deleteById(Long id);

}
