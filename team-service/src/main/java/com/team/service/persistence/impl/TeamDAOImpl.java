package com.team.service.persistence.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.team.service.models.Team;
import com.team.service.persistence.ITeamDAO;
import com.team.service.persistence.repositories.TeamRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeamDAOImpl implements ITeamDAO {

    private final TeamRepository teamRepository;

    @Override
    public List<Team> findAll() {
        return this.teamRepository.findAll();
    }

    @Override
    public List<Team> findByClub(Long club_id) {
        return this.teamRepository.findAllByClubId(club_id);
    }

    @Override
    public Optional<Team> findById(Long id) {
        return this.teamRepository.findById(id);
    }

    @Override
    public void save(Team team) {
        this.teamRepository.save(team);
    }

    @Override
    public void deleteById(Long id) {
        this.teamRepository.deleteById(id);
    }

    @Override
    public Optional<Team> findByName(String name) {
        return Optional.ofNullable(this.teamRepository.findTeamByNombre(name));
    }
    
}
