package com.jugador.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;
import com.jugador.service.persistence.IJugadorDAO;
import com.jugador.service.services.JugadorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JugadorServiceImpl implements JugadorService {

    private final IJugadorDAO iJugadorDAO;

    @Override
    public List<Jugador> findAll() {
        return this.iJugadorDAO.findAll();
    }

    @Override
    public List<Jugador> findByEstado(EstadoJugador estado, Long equipoId) {
        return this.iJugadorDAO.findByEstado(estado, equipoId);
    }

    @Override
    public List<Jugador> findByEquipo(Long equipo_id) {
        return this.iJugadorDAO.findByEquipo(equipo_id);
    }

    @Override
    public List<Jugador> findAllByEmailOrDocumento(String email, String documento) {
        return this.iJugadorDAO.findAllByEmailOrDocumento(email, documento);
    }

    @Override
    public Optional<Jugador> findByEquipoAndCamiseta(Long equipoId, int numero_camiseta) {
        return this.iJugadorDAO.findByEquipoAndCamiseta(equipoId, numero_camiseta);
    }

    @Override
    public Optional<Jugador> findById(Long id) {
        return this.iJugadorDAO.findById(id);
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
