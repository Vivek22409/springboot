package com.advenspirit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.advenspirit.controller","com.advenspirit.service"})
public class SpringbootMyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMyappApplication.class, args);
	}

}
