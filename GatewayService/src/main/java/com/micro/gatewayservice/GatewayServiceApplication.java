package com.micro.gatewayservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.context.annotation.Bean;

import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}


	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(
			DiscoveryLocatorProperties dlp, ReactiveDiscoveryClient rdc) {
		return new DiscoveryClientRouteDefinitionLocator( rdc, dlp);
	}


}
