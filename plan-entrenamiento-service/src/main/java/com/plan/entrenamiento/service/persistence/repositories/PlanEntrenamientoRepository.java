package com.plan.entrenamiento.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plan.entrenamiento.service.models.PlanEntrenamiento;

public interface PlanEntrenamientoRepository extends JpaRepository<PlanEntrenamiento, Long> {

    // Query methods to find an especific training plan

    // Buscar todos los planes de entrenamiento asociados a un equipo
    List<PlanEntrenamiento> findByEquipo(Long equipo_id);

    // Buscar un plan de entrenamiento por un nombre especifico
    PlanEntrenamiento findByNombre(String nombre);
    
}
