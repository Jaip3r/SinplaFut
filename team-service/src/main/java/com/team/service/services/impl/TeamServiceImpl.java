package com.team.service.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team.service.client.CuerpoTecnicoClient;
import com.team.service.client.JugadorClient;
import com.team.service.client.SesionEntrenamientoClient;
import com.team.service.controllers.dtos.CuerpoTecnicoDTO;
import com.team.service.controllers.dtos.JugadorDTO;
import com.team.service.controllers.dtos.SesionEntrenamientoDTO;
import com.team.service.models.Team;
import com.team.service.persistence.ITeamDAO;
import com.team.service.services.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final ITeamDAO teamDAO;
    private final CuerpoTecnicoClient cTecnicoClient;
    private final SesionEntrenamientoClient sEntrenamientoClient;
    private final JugadorClient jugadorClient;

    @Override
    public List<Team> findAll() {
        return this.teamDAO.findAll();
    }

    @Override
    public List<Team> findByClub(Long clubId) {
        return this.teamDAO.findByClub(clubId);
    }

    @Override
    public Team findByName(String name) {
        var team = this.teamDAO.findByName(name)
                .orElse(null);
        return team;
    }

    @Override
    public Team findById(Long id) {
        var team = this.teamDAO.findById(id)
                .orElse(null);
        return team;
    }

    @Override
    public void save(Team team) {
        this.teamDAO.save(team);
    }

    @Override
    public void delete(Long id) {
        this.teamDAO.deleteById(id);
    }

    @Override
    public List<CuerpoTecnicoDTO> findStaffByEquipoId(Long equipoId) {
        // Obtener el staff técnico - consultando al microservicio respectivo
        return this.cTecnicoClient.findByEquipo(equipoId);
    }

    @Override
    public List<SesionEntrenamientoDTO> findSesionesByEquipoId(Long equipoId) {
        return this.sEntrenamientoClient.findByEquipo(equipoId);
    }

    @Override
    public List<JugadorDTO> findJugadorByEquipoId(Long equipoId) {
        // Obtener los jugadores del equipo - consultando al microservicio respectivo
        return this.jugadorClient.findByEquipo(equipoId);
    }

    @Override
    public List<JugadorDTO> findJugadorByEstado(Long equipoId, String estado) {
        // Obtener los jugadores del equipo por estado - consultando al microservicio respectivo
        return this.jugadorClient.findByEstado(equipoId, estado);
    }
    
}
