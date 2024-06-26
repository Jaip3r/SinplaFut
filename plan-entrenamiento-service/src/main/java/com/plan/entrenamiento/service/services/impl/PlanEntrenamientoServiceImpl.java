package com.plan.entrenamiento.service.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.plan.entrenamiento.service.models.PlanEntrenamiento;
import com.plan.entrenamiento.service.persistence.IPlanEntrenamientoDAO;
import com.plan.entrenamiento.service.services.PlanEntrenamientoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanEntrenamientoServiceImpl implements PlanEntrenamientoService {

    private final IPlanEntrenamientoDAO iPlanEntrenamientoDAO;

    @Override
    public List<PlanEntrenamiento> findAll() {
        return this.iPlanEntrenamientoDAO.findAll();
    }

    @Override
    public List<PlanEntrenamiento> findByEquipo(Long equipo_id) {
        return this.iPlanEntrenamientoDAO.findByEquipo(equipo_id);
    }

    @Override
    public Optional<PlanEntrenamiento> findByNombre(String nombre) {
        return this.iPlanEntrenamientoDAO.findByNombre(nombre);
    }

    @Override
    public Optional<PlanEntrenamiento> findById(Long id) {
        return this.iPlanEntrenamientoDAO.findById(id);
    }

    @Override
    public void save(PlanEntrenamiento plan) {
        this.iPlanEntrenamientoDAO.save(plan);
    }

    @Override
    public void deleteById(Long id) {
        this.iPlanEntrenamientoDAO.deleteById(id);
    }
    
}
