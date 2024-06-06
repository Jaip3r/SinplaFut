package com.team.service.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.service.config.LoadBalancerConfiguration;
import com.team.service.controllers.dtos.PlanEntrenamientoDTO;

@FeignClient(name = "plan-entrenamiento-service")
@LoadBalancerClient(name = "plan-entrenamiento-service", configuration = LoadBalancerConfiguration.class)
public interface PlanEntrenamientoClient {
    @GetMapping("/plan-entrenamiento-service/sesion/findByEquipo/{equipoId}")
    List<PlanEntrenamientoDTO> findByEquipo(@PathVariable Long equipoId);
}
