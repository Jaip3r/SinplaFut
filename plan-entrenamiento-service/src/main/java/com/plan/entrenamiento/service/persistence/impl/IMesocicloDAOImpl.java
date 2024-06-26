package com.plan.entrenamiento.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.plan.entrenamiento.service.models.Mesociclo;
import com.plan.entrenamiento.service.persistence.IMesocicloDAO;
import com.plan.entrenamiento.service.persistence.repositories.MesocicloRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IMesocicloDAOImpl implements IMesocicloDAO {

    private final MesocicloRepository mRepository;

    @Override
    public List<Mesociclo> findAll() {
        return this.mRepository.findAll();
    }

    @Override
    public Optional<Mesociclo> findById(Long id) {
        return this.mRepository.findById(id);
    }

    @Override
    public void save(Mesociclo mesociclo) {
        this.mRepository.save(mesociclo);
    }

    @Override
    public void deleteById(Long id) {
        this.mRepository.deleteById(id);
    }
    
}
