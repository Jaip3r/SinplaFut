package com.jugador.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;
import com.jugador.service.persistence.IJugadorDAO;
import com.jugador.service.persistence.repositories.JugadorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class jugadorDAOImpl implements IJugadorDAO {

    private final JugadorRepository jugadorRepository;

    @Override
    public List<Jugador> findAll() {
        return this.jugadorRepository.findAll();
    }

    @Override
    public List<Jugador> findByEstado(EstadoJugador estado) {
        return this.jugadorRepository.findAllByEstado(estado);
    }

    @Override
    public List<Jugador> findByEquipo(Long equipo_id) {
        return this.jugadorRepository.findByEquipoId(equipo_id);
    }

    @Override
    public Optional<Jugador> findById(Long id) {
        return this.jugadorRepository.findById(id);
    }

    @Override
    public Optional<Jugador> findByDocumento(String documento) {
        return Optional.ofNullable(this.jugadorRepository.findByDocumento(documento));
    }

    @Override
    public Optional<Jugador> findByEmail(String email) {
        return Optional.ofNullable(this.jugadorRepository.findByEmail(email));
    }

    @Override
    public void save(Jugador jugador) {
        this.jugadorRepository.save(jugador);
    }

    @Override
    public void deleteById(Long id) {
        this.jugadorRepository.deleteById(id);
    }
    
}
