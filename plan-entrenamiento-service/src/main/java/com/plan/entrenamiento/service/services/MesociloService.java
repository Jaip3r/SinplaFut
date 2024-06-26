package com.plan.entrenamiento.service.services;

import java.util.List;
import java.util.Optional;

import com.plan.entrenamiento.service.models.Mesociclo;

public interface MesociloService {

    List<Mesociclo> findAll();

    Optional<Mesociclo> findById(Long id);

    void save(Mesociclo mesociclo);

    void deleteById(Long id);
    
}
