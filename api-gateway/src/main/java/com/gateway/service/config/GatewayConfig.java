package com.gateway.service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GatewayConfig {

    @Bean
    @Profile(value = "eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {

        return builder
                .routes()
                .route(route -> route
                    .path("/club-service/club/*")
                    .uri("http://localhost:8081")
                )
                .route(route -> route
                    .path("/team-service/team/*")
                    .uri("http://localhost:8082")
                )
                .build();

    }

    @Bean
    @Profile(value = "eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {

        return builder
                .routes()
                .route(route -> route
                    .path("/club-service/club/**")
                    .uri("lb://club-service")
                )
                .route(route -> route
                    .path("/team-service/team/**")
                    .uri("lb://team-service")
                )
                .build();

    }
    
}
