package com.club.service.persistence.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.club.service.models.Club;
import com.club.service.persistence.IClubDAO;
import com.club.service.persistence.repositories.ClubRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClubDAOImpl implements IClubDAO {

    private final ClubRepository clubRepository;

    @Override
    public List<Club> findAll() {
        return this.clubRepository.findAll();
    }

    @Override
    public Optional<Club> findById(Long id) {
        return this.clubRepository.findById(id);
    }

    @Override
    public Optional<Club> findByStadium(String estadio) {
        return this.clubRepository.findByEstadio(estadio);
    }

    @Override
    public void save(Club club) {
        this.clubRepository.save(club);
    }

    @Override
    public void deleteById(Long id) {
        this.clubRepository.deleteById(id);
    }
    
}
