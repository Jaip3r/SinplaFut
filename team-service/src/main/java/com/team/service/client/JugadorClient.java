package com.team.service.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.service.config.LoadBalancerConfiguration;
import com.team.service.controllers.dtos.JugadorDTO;

@FeignClient(name = "jugador-service")
@LoadBalancerClient(name = "jugador-service", configuration = LoadBalancerConfiguration.class)
public interface JugadorClient {

    @GetMapping("/jugador-service/jugador/findByEquipo/{equipoId}")
    List<JugadorDTO> findByEquipo(@PathVariable Long equipoId);

    @GetMapping("/jugador-service/jugador/findByEquipo/{equipoId}/estado/{estado}")
    List<JugadorDTO> findByEstado(@PathVariable Long equipoId, @PathVariable String estado);
    
}
