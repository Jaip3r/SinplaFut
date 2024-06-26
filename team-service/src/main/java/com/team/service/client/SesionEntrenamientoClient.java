package com.team.service.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.service.config.LoadBalancerConfiguration;
import com.team.service.controllers.dtos.SesionEntrenamientoDTO;

@FeignClient(name = "sesion-entrenamiento-service")
@LoadBalancerClient(name = "sesion-entrenamiento-service", configuration = LoadBalancerConfiguration.class)
public interface SesionEntrenamientoClient {

    @GetMapping("/sesion-entrenamiento-service/sesion/findByEquipo/{equipoId}")
    List<SesionEntrenamientoDTO> findByEquipo(@PathVariable Long equipoId);

}
