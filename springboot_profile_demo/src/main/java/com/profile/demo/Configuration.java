package com.profile.demo;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;


@SpringBootConfiguration
public class Configuration {	
	
	@Bean
	@Profile("dev")
	public String getdevBean() {
		System.out.println("*********");
		return "dev-profile";
	}
	
	@Bean
	@Profile("prod")
	public String getprodBean() {
		System.out.println("###########");
		return "dev-profile";
	}
	
	
}
