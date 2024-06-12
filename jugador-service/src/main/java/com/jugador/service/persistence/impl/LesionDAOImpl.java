package com.jugador.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jugador.service.models.Lesion;
import com.jugador.service.persistence.ILesionDAO;
import com.jugador.service.persistence.repositories.LesionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LesionDAOImpl implements ILesionDAO {

    private final LesionRepository lRepository;

    @Override
    public List<Lesion> findAll() {
        return this.lRepository.findAll();
    }

    @Override
    public Optional<Lesion> findById(Long id) {
        return this.lRepository.findById(id);
    }

    @Override
    public Optional<Lesion> findByNombre(String nombre) {
        return Optional.ofNullable(this.lRepository.findByNombre(nombre));
    }

    @Override
    public void save(Lesion lesion) {
        this.lRepository.save(lesion);
    }

    @Override
    public void deleteById(Long id) {
        this.lRepository.deleteById(id);
    }
    
}
