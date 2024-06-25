package com.sesion.entrenamiento.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesion.entrenamiento.service.models.SesionEntrenamiento;

public interface SesionEntrenamientoRepository extends JpaRepository<SesionEntrenamiento, Long> {

    // Query methods to find an especific session training
    List<SesionEntrenamiento> findByEquipoId(Long equipo_id);
    
}
