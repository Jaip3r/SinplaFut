package com.club.service.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.club.service.models.Club;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByEstadio(String estadio);
    
}
