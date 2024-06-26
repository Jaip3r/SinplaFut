package com.plan.entrenamiento.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.plan.entrenamiento.service.models.Microciclo;
import com.plan.entrenamiento.service.persistence.IMicrocicloDAO;
import com.plan.entrenamiento.service.persistence.repositories.MicrocicloRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IMicrocicloDAOImpl implements IMicrocicloDAO {

    private final MicrocicloRepository microRepository;

    @Override
    public List<Microciclo> findAll() {
        return this.microRepository.findAll();
    }

    @Override
    public Optional<Microciclo> findByNombre(String nombre) {
        return Optional.ofNullable(this.microRepository.findByNombre(nombre));
    }

    @Override
    public Optional<Microciclo> findById(Long id) {
        return this.microRepository.findById(id);
    }

    @Override
    public void save(Microciclo microciclo) {
        this.microRepository.save(microciclo);
    }

    @Override
    public void deleteById(Long id) {
        this.microRepository.deleteById(id);
    }
    
}
