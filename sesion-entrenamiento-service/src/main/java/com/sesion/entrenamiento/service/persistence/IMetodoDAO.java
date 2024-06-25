package com.sesion.entrenamiento.service.persistence;

import java.util.List;
import java.util.Optional;

import com.sesion.entrenamiento.service.models.MetodoEntrenamiento;

public interface IMetodoDAO {

    List<MetodoEntrenamiento> findAll();

    Optional<MetodoEntrenamiento> findById(Long id);

    Optional<MetodoEntrenamiento> findByNombre(String nombre);

    void save(MetodoEntrenamiento metodo);

    void deleteById(Long id);
    
}
