package com.club.service.services;

import java.util.List;

import com.club.service.controllers.DTO.ClubResponseDTO;
import com.club.service.models.Club;

public interface ClubService {

    List<ClubResponseDTO> findAll();

    ClubResponseDTO findById(Long id);

    ClubResponseDTO findByStadium(String estadio);

    void save(Club club);

    void deleteById(Long id);
    
}
