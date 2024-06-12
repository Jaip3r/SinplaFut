package com.jugador.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jugador.service.models.Lesion;
import com.jugador.service.persistence.ILesionDAO;
import com.jugador.service.services.LesionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LesionServiceImpl implements LesionService {

    private final ILesionDAO iLesionDAO;

    @Override
    public List<Lesion> findAll() {
        return this.iLesionDAO.findAll();
    }

    @Override
    public Optional<Lesion> findById(Long id) {
        return this.iLesionDAO.findById(id);
    }

    @Override
    public Optional<Lesion> findByNombre(String nombre) {
        return this.iLesionDAO.findByNombre(nombre);
    }

    @Override
    public void save(Lesion lesion) {
        this.iLesionDAO.save(lesion);
    }

    @Override
    public void deleteById(Long id) {
        this.iLesionDAO.deleteById(id);
    }
    
}
