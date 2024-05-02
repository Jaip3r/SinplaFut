package com.team.service.persistence;

import java.util.List;
import java.util.Optional;

import com.team.service.models.Team;

public interface ITeamDAO {

    List<Team> findAll();

    List<Team> findByClub(Long club_id);

    Optional<Team> findById(Long id);

    void save(Team team);

    void deleteById(Long id);
    
}
