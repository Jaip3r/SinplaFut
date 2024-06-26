package com.plan.entrenamiento.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plan.entrenamiento.service.models.Mesociclo;

public interface MesocicloRepository extends JpaRepository<Mesociclo, Long> {
    
}
