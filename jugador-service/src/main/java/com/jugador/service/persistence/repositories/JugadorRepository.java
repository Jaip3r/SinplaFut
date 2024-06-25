package com.jugador.service.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long>{

    // Query methods to find an especific player

    // Buscar todos los jugadores que coinciden con correo electrónico o número de documento o número de camiseta
    @Query("SELECT j FROM Jugador j WHERE j.email = ?1 OR j.documento = ?2 OR j.numero_camiseta = ?3")
    List<Jugador> findAllByEmailOrDocumentoOrCamiseta(String email, String documento, int numero_camiseta);

    // Buscar todos los jugadores por estado y equipo
    List<Jugador> findAllByEstadoAndEquipoId(EstadoJugador estado, Long equipoId);

    // Buscar todos los jugadores por equipo
    List<Jugador> findByEquipoId(Long equipo_id);
    
}
