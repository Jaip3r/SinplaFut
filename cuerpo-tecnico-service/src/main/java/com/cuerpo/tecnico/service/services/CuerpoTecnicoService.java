package com.cuerpo.tecnico.service.services;

import java.util.List;
import java.util.Optional;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;

public interface CuerpoTecnicoService {

    List<CuerpoTecnico> findAll();

    List<CuerpoTecnico> findByTipo(CuerpoTecnicoType tipo, Long equipoId);

    List<CuerpoTecnico> findByEquipo(Long equipo_id);

    List<CuerpoTecnico> findAllByEmailOrDocumento(String email, String documento);

    Optional<CuerpoTecnico> findById(Long id);

    Optional<CuerpoTecnico> findByEmailOrDocumento(String email, String documento);

    void save(CuerpoTecnico cuerpoTecnico);

    void deleteById(Long id);
    
}
