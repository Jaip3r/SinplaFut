package com.club.service.services.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.club.service.exception.ResourceNotFoundException;
import com.club.service.models.Club;
import com.club.service.persistence.IClubDAO;
import com.club.service.services.ClubService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClubServiceImpl implements ClubService {

    private final IClubDAO iClubDAO;
    
    @Override
    public List<Club> findAll() {
        return this.iClubDAO.findAll();
    }

    @Override
    public Club findById(Long id) {
        
        var club = this.iClubDAO.findById(id)
            .orElseThrow(null);

        return club;

    }

    @Override
    public Club findByStadium(String estadio) {

        var club = this.iClubDAO.findByStadium(estadio)
            .orElse(null);

        return club;

    }

    @Override
    public void save(Club club) {
        this.iClubDAO.save(club);
    }

    @Override
    public void deleteById(Long id) {
        this.iClubDAO.deleteById(id);
    }
    
}
