package com.cuerpo.tecnico.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CuerpoTecnicoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CuerpoTecnicoServiceApplication.class, args);
	}

}
