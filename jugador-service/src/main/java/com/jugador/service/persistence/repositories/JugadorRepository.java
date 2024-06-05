package com.jugador.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jugador.service.models.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long>{

    // Query methods to find an especific player
    Jugador findByEmail(String email);

    List<Jugador> findByEquipoId(Long equipo_id);
    
}
