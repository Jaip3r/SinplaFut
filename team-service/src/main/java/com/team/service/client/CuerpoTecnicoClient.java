package com.team.service.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.service.config.LoadBalancerConfiguration;
import com.team.service.controllers.dtos.CuerpoTecnicoDTO;

@FeignClient(name = "cuerpo-tecnico-service")
@LoadBalancerClient(name = "cuerpo-tecnico-service", configuration = LoadBalancerConfiguration.class)
public interface CuerpoTecnicoClient {

    @GetMapping("/cuerpo-tecnico-service/staff/findByEquipo/{equipoId}")
    List<CuerpoTecnicoDTO> findByEquipo(@PathVariable Long equipoId);
    
}
