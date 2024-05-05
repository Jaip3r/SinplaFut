package com.club.service.services;

import java.util.List;

import com.club.service.controllers.DTO.TeamDTO;
import com.club.service.models.Club;

public interface ClubService {

    List<Club> findAll();

    Club findById(Long id);

    Club findByStadium(String estadio);

    void save(Club club);

    void deleteById(Long id);

    // Peticiones a otros MS

    List<TeamDTO> findTeamsByClubId(Long id);
    
}
