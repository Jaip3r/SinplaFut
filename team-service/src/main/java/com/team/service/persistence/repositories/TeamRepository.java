package com.team.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.team.service.models.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

    // Query method to find all teams assigned to a specific club
    List<Team> findAllByClubId(Long club_id);

    @Query("SELECT t FROM Team t WHERE t.nombre = ?1")
    Team findTeamByNombre(String name);
    
}
