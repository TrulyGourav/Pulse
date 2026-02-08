package com.project.event_ingestion_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class EventIngestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventIngestionServiceApplication.class, args);
	}

}
