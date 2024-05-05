package com.cuerpo.tecnico.service.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;

public interface CuerpoTecnicoRepository extends JpaRepository<CuerpoTecnico, Long>{

    // Query methods to find an especific technical staff
    CuerpoTecnico findByDocumento(String documento);

    CuerpoTecnico findByEmail(String email);
    
}
