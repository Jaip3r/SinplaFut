package com.team.service.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.team.service.models.Team;
import com.team.service.persistence.ITeamDAO;
import com.team.service.services.TeamService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final ITeamDAO teamDAO;

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
    
}
