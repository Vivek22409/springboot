package com.advenspirit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication(scanBasePackages = {"com.advenspirit.config","com.advenspirit.controller","com.advenspirit.service","com.advenspirit.util","com.advenspirit.security"})
public class SpringbootMyappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMyappApplication.class, args);
	}

}
