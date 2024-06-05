package com.plan.entrenamiento.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.plan.entrenamiento.service.models.MetodoEntrenamiento;
import com.plan.entrenamiento.service.persistence.IMetodoDAO;
import com.plan.entrenamiento.service.persistence.repositories.MetodoEntrenamientoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IMetodoDAOImpl implements IMetodoDAO {

    private final MetodoEntrenamientoRepository mRepository;

    @Override
    public List<MetodoEntrenamiento> findAll() {
        return this.mRepository.findAll();
    }

    @Override
    public Optional<MetodoEntrenamiento> findById(Long id) {
        return this.mRepository.findById(id);
    }

    @Override
    public Optional<MetodoEntrenamiento> findByNombre(String nombre) {
        return Optional.ofNullable(this.mRepository.findByNombre(nombre));
    }

    @Override
    public void save(MetodoEntrenamiento metodo) {
        this.mRepository.save(metodo);
    }

    @Override
    public void deleteById(Long id) {
        this.mRepository.deleteById(id);
    }
    
}
