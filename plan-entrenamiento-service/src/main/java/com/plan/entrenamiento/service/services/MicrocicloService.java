package com.plan.entrenamiento.service.services;

import java.util.List;
import java.util.Optional;

import com.plan.entrenamiento.service.models.Microciclo;

public interface MicrocicloService {

    List<Microciclo> findAll();

    Optional<Microciclo> findByNombre(String nombre);

    Optional<Microciclo> findById(Long id);

    void save(Microciclo microciclo);

    void deleteById(Long id);
    
}
