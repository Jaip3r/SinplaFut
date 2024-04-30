package com.club.service.persistence;

import java.util.List;
import java.util.Optional;

import com.club.service.models.Club;

public interface IClubDAO {

    List<Club> findAll();

    Optional<Club> findById(Long id);

    Optional<Club> findByStadium(String estadio);

    void save(Club club);

    void deleteById(Long id);
    
}
