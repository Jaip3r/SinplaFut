package com.club.service.config;

import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadBalancerConfiguration {

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(ConfigurableApplicationContext context){
        log.info("Configurando balanceador de carga");
        return ServiceInstanceListSupplier
                .builder()
                .withBlockingDiscoveryClient() // La obtención de la lista de instancias sera asincrona.
                .withSameInstancePreference() // prefiere la misma instancia del servicio en llamadas sucesivas.
                .build(context);
    }
    
}
