package com.team.service.services;

import java.util.List;

import com.team.service.controllers.dtos.CuerpoTecnicoDTO;
import com.team.service.controllers.dtos.PlanEntrenamientoDTO;
import com.team.service.models.Team;

public interface TeamService {

    List<Team> findAll();

    List<Team> findByClub(Long clubId);

    Team findById(Long id);

    Team findByName(String name);

    void save(Team team);

    void delete(Long id);

    // Peticiones a otros MS

    List<CuerpoTecnicoDTO> findStaffByEquipoId(Long equipoId);

    List<PlanEntrenamientoDTO> findSesionesByEquipoId(Long equipoId);
    
}
