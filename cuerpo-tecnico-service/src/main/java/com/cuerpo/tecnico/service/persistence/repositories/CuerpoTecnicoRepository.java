package com.cuerpo.tecnico.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;

public interface CuerpoTecnicoRepository extends JpaRepository<CuerpoTecnico, Long>{

    // Query methods to find an especific technical staff
    CuerpoTecnico findByDocumento(String documento);

    CuerpoTecnico findByEmail(String email);

    List<CuerpoTecnico> findAllByTipo(CuerpoTecnicoType tipo);

    List<CuerpoTecnico> findAllByEquipoId(Long equipo_id);
    
}
