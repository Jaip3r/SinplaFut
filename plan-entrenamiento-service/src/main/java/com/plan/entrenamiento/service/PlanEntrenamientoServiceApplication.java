package com.plan.entrenamiento.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PlanEntrenamientoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanEntrenamientoServiceApplication.class, args);
	}

}
