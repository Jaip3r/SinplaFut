package com.plan.entrenamiento.service.persistence;

import java.util.List;
import java.util.Optional;

import com.plan.entrenamiento.service.models.Microciclo;

public interface IMicrocicloDAO {

    List<Microciclo> findAll();

    Optional<Microciclo> findByNombre(String nombre);

    Optional<Microciclo> findById(Long id);

    void save(Microciclo microciclo);

    void deleteById(Long id);
    
}
