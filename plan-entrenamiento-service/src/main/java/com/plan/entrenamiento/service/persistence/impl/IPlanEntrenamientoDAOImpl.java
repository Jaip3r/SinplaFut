package com.plan.entrenamiento.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.plan.entrenamiento.service.models.PlanEntrenamiento;
import com.plan.entrenamiento.service.persistence.IPlanEntrenamientoDAO;
import com.plan.entrenamiento.service.persistence.repositories.PlanEntrenamientoRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IPlanEntrenamientoDAOImpl implements IPlanEntrenamientoDAO {

    private final PlanEntrenamientoRepository pRepository;

    @Override
    public List<PlanEntrenamiento> findAll() {
        return this.pRepository.findAll();
    }

    @Override
    public List<PlanEntrenamiento> findByEquipo(Long equipo_id) {
        return this.pRepository.findByEquipo(equipo_id);
    }

    @Override
    public Optional<PlanEntrenamiento> findByNombre(String nombre) {
        return Optional.ofNullable(this.pRepository.findByNombre(nombre));
    }

    @Override
    public Optional<PlanEntrenamiento> findById(Long id) {
        return this.pRepository.findById(id);
    }

    @Override
    public void save(PlanEntrenamiento plan) {
        this.pRepository.save(plan);
    }

    @Override
    public void deleteById(Long id) {
        this.pRepository.deleteById(id);
    }
    
}
