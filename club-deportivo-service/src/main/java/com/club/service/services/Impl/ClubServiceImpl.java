package com.club.service.services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.club.service.controllers.DTO.ClubResponseDTO;
import com.club.service.models.Club;
import com.club.service.persistence.IClubDAO;
import com.club.service.services.ClubService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final IClubDAO iClubDAO;
    
    @Override
    public List<ClubResponseDTO> findAll() {
        
        return this.iClubDAO.findAll().stream()
                .map(this::clubToResponseDTO).toList();

    }

    @Override
    public ClubResponseDTO findById(Long id) {
        
        var club = this.iClubDAO.findById(id)
            .orElseThrow();

        return this.clubToResponseDTO(club);

    }

    @Override
    public ClubResponseDTO findByStadium(String estadio) {

        var club = this.iClubDAO.findByStadium(estadio)
            .orElseThrow();

        return this.clubToResponseDTO(club);

    }

    @Override
    public void save(Club club) {
        this.iClubDAO.save(club);
    }

    @Override
    public void deleteById(Long id) {
        this.iClubDAO.deleteById(id);
    }

    private ClubResponseDTO clubToResponseDTO(Club club) {

        return ClubResponseDTO.builder()
                .id(club.getId())
                .nombre(club.getNombre())
                .direccion(club.getDireccion())
                .telefono(club.getTelefono())
                .ciudad(club.getCiudad())
                .pais(club.getPais())
                .estadio(club.getEstadio())
                .logoUrl(club.getLogoUrl())
                .build();

    }
    
}
