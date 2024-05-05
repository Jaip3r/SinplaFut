package com.club.service.client;

import java.util.List;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.club.service.config.LoadBalancerConfiguration;
import com.club.service.controllers.DTO.TeamDTO;

@FeignClient(name = "team-service")
@LoadBalancerClient(name = "team-service", configuration = LoadBalancerConfiguration.class)
public interface TeamClient {

    @GetMapping("/team-service/team/findByClub/{clubId}")
    List<TeamDTO> findByClub(@PathVariable Long clubId);
    
}
