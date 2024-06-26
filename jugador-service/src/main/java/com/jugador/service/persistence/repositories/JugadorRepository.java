package com.jugador.service.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jugador.service.models.EstadoJugador;
import com.jugador.service.models.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long>{

    // Query methods to find an especific player

    // Buscar Jugador del mismo equipo con cierto número de camiseta
    @Query("SELECT j FROM Jugador j WHERE j.equipoId = ?1 AND j.numero_camiseta = ?2")
    Optional<Jugador> findByEquipoIdAndCamiseta(Long equipoId, int numero_camiseta);

    // Buscar todos los jugadores que coinciden con correo electrónico o número de documento
    List<Jugador> findAllByEmailOrDocumento(String email, String documento);

    // Buscar todos los jugadores por estado y equipo
    List<Jugador> findAllByEstadoAndEquipoId(EstadoJugador estado, Long equipoId);

    // Buscar todos los jugadores por equipo
    List<Jugador> findByEquipoId(Long equipo_id);
    
}
