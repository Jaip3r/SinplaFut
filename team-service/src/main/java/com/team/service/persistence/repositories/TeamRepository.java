package com.team.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.service.models.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

    // Query method to find all teams assigned to a specific club
    List<Team> findAllByClubId(Long club_id);
    
}
