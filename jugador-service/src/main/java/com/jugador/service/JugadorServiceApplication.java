package com.jugador.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JugadorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JugadorServiceApplication.class, args);
	}

}
