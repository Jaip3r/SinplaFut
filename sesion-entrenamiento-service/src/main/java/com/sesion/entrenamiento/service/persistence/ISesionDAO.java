package com.sesion.entrenamiento.service.persistence;

import java.util.List;
import java.util.Optional;

import com.sesion.entrenamiento.service.models.SesionEntrenamiento;

public interface ISesionDAO {

    List<SesionEntrenamiento> findAll();

    List<SesionEntrenamiento> findByEquipo(Long equipo_id);

    Optional<SesionEntrenamiento> findById(Long id);

    void save(SesionEntrenamiento sesion);

    void deleteById(Long id);
    
}
