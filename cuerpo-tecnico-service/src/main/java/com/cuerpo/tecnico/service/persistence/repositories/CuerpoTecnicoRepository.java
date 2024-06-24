package com.cuerpo.tecnico.service.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cuerpo.tecnico.service.models.CuerpoTecnico;
import com.cuerpo.tecnico.service.models.CuerpoTecnicoType;

public interface CuerpoTecnicoRepository extends JpaRepository<CuerpoTecnico, Long>{

    // Query methods to find an especific technical staff

    // Buscar un jugador por correo electrónico o número de documento
    Optional<CuerpoTecnico> findFirstByEmailOrDocumento(String email, String documento);

    // Buscar todos los jugadores que coinciden con correo electrónico o número de documento
    List<CuerpoTecnico>  findAllByEmailOrDocumento(String email, String documento);

    List<CuerpoTecnico> findAllByTipoAndEquipoId(CuerpoTecnicoType tipo, Long equipoId);

    List<CuerpoTecnico> findAllByEquipoId(Long equipo_id);
    
}
