package com.cuerpo.tecnico.service.persistence;

import java.util.List;
import java.util.Optional;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;

public interface ICuerpoTecnicoDAO {

    List<CuerpoTecnico> findAll();

    List<CuerpoTecnico> findByTipo(CuerpoTecnicoType tipo, Long equipoId);

    List<CuerpoTecnico> findByEquipo(Long equipo_id);

    Optional<CuerpoTecnico> findById(Long id);

    Optional<CuerpoTecnico> findByDocumento(String documento);

    Optional<CuerpoTecnico> findByEmail(String email);

    void save(CuerpoTecnico cuerpoTecnico);

    void deleteById(Long id);
    
}
