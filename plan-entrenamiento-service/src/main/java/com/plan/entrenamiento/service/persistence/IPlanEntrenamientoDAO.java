package com.plan.entrenamiento.service.persistence;

import java.util.List;
import java.util.Optional;

import com.plan.entrenamiento.service.models.PlanEntrenamiento;


public interface IPlanEntrenamientoDAO {

    List<PlanEntrenamiento> findAll();

    List<PlanEntrenamiento> findByEquipo(Long equipo_id);

    Optional<PlanEntrenamiento> findByNombre(String nombre);

    Optional<PlanEntrenamiento> findById(Long id);

    void save(PlanEntrenamiento plan);

    void deleteById(Long id);
    
}