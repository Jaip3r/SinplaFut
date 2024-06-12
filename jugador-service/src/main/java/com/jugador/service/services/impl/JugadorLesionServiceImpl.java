package com.jugador.service.services.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jugador.service.models.JugadorLesion;
import com.jugador.service.persistence.IJugadorLesionDAO;
import com.jugador.service.services.JugadorLesionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JugadorLesionServiceImpl implements JugadorLesionService {

    private final IJugadorLesionDAO iJugadorLesionDAO;

    @Override
    public Optional<JugadorLesion> findRecordByJugadorIdAndLesionId(Long jugador_id, Long lesion_id) {
        return this.iJugadorLesionDAO.findRecordByJugadorIdAndLesionId(jugador_id, lesion_id);
    }

    @Override
    public void save(JugadorLesion jugadorLesion) {
        this.iJugadorLesionDAO.save(jugadorLesion);
    }

    @Override
    public void deleteById(Long id) {
        this.iJugadorLesionDAO.deleteById(id);
    }
    
}
