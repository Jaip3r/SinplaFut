package com.club.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClubDeportivoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubDeportivoServiceApplication.class, args);
	}

}
