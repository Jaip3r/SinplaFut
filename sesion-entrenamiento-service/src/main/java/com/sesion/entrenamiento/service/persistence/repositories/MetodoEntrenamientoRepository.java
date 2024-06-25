package com.sesion.entrenamiento.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesion.entrenamiento.service.models.MetodoEntrenamiento;


public interface MetodoEntrenamientoRepository extends JpaRepository<MetodoEntrenamiento, Long> {

    // Query methods to find an especific training method
    MetodoEntrenamiento findByNombre(String nombre);
    
}
