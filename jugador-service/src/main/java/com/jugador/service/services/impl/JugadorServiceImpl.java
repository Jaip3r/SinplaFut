package com.jugador.service.services.impl;

import java.util.List;
import java.util.Optional;

import com.jugador.service.models.Jugador;
import com.jugador.service.persistence.IJugadorDAO;
import com.jugador.service.services.JugadorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JugadorServiceImpl implements JugadorService {

    private final IJugadorDAO iJugadorDAO;

    @Override
    public List<Jugador> findAll() {
        return this.iJugadorDAO.findAll();
    }

    @Override
    public List<Jugador> findByEquipo(Long equipo_id) {
        return this.iJugadorDAO.findByEquipo(equipo_id);
    }

    @Override
    public Optional<Jugador> findById(Long id) {
        return this.iJugadorDAO.findById(id);
    }

    @Override
    public Optional<Jugador> findByEmail(String email) {
        return this.iJugadorDAO.findByEmail(email);
    }

    @Override
    public void save(Jugador jugador) {
        this.iJugadorDAO.save(jugador);
    }

    @Override
    public void deleteById(Long id) {
        this.iJugadorDAO.deleteById(id);
    }
    
}