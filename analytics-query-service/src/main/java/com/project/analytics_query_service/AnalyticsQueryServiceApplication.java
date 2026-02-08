package com.project.analytics_query_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class AnalyticsQueryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticsQueryServiceApplication.class, args);
	}

}
