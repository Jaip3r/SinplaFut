package com.jugador.service.persistence.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jugador.service.models.Jugador;
import com.jugador.service.models.JugadorLesion;
import com.jugador.service.models.Lesion;
import com.jugador.service.persistence.IJugadorLesionDAO;
import com.jugador.service.persistence.repositories.JugadorLesionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JugadorLesionDAOImpl implements IJugadorLesionDAO {

    private final JugadorLesionRepository jLesionRepository;

    @Override
    public Optional<JugadorLesion> findRecordByJugadorIdAndLesionId(Long jugador_id, Long lesion_id) {
        return this.jLesionRepository.findByJugadorIdAndLesionId(jugador_id, lesion_id);
    }

    @Override
    public Optional<JugadorLesion> findJugadorLesion(Jugador jugador, Lesion lesion, LocalDate fecha_inicio,
            LocalDate fecha_fin) {
        return this.jLesionRepository.findJugadorLesion(jugador, lesion, fecha_inicio, fecha_fin);
    }

    @Override
    public Optional<JugadorLesion> findById(Long id) {
        return this.jLesionRepository.findById(id);
    }

    @Override
    public void save(JugadorLesion jugadorLesion) {
        this.jLesionRepository.save(jugadorLesion);
    }

    @Override
    public void deleteById(Long id) {
        this.jLesionRepository.deleteById(id);
    }
    
}
