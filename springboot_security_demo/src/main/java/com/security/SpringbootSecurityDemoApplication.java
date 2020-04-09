package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages={"com.security.controller","com.security.service","com.security.configurer","com.security.util","com.security.filters"})
public class SpringbootSecurityDemoApplication {

	public static void main(String[] args) {		
		SpringApplication.run(SpringbootSecurityDemoApplication.class, args);
	}	

}

