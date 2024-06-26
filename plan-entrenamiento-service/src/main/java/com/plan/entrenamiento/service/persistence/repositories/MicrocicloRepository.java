package com.plan.entrenamiento.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.plan.entrenamiento.service.models.Microciclo;

public interface MicrocicloRepository extends JpaRepository<Microciclo, Long>  {

    // Query method to find an specific microciclo
    Microciclo findByNombre(String nombre);
    
}
