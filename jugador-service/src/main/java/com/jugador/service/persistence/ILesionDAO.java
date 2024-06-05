package com.jugador.service.persistence;

import java.util.List;
import java.util.Optional;

import com.jugador.service.models.Lesion;

public interface ILesionDAO {

    List<Lesion> findAll();

    Optional<Lesion> findById(Long id);

    Optional<Lesion> findByNombre(String nombre);

    void save(Lesion lesion);

    void deleteById(Long id);
    
}
