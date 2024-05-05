package com.cuerpo.tecnico.service.services;

import java.util.List;
import java.util.Optional;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;

public interface CuerpoTecnicoService {

    List<CuerpoTecnico> findAll();

    Optional<CuerpoTecnico> findById(Long id);

    Optional<CuerpoTecnico> findByDocumento(String documento);

    Optional<CuerpoTecnico> findByEmail(String email);

    void save(CuerpoTecnico cuerpoTecnico);

    void deleteById(Long id);
    
}
