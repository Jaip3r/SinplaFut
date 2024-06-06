package com.jugador.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jugador.service.models.Lesion;


public interface LesionRepository extends JpaRepository<Lesion, Long> {

    // Query methods to find an especific injury
    Lesion findByNombre(String nombre);
    
}
