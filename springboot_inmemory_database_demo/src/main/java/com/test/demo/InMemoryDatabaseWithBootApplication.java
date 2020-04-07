package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.test.controller","com.test.entity","com.test.repository"})
@EnableJpaRepositories("com.test.repository")
@EntityScan("com.test.entity")
public class InMemoryDatabaseWithBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(InMemoryDatabaseWithBootApplication.class, args);
	}
}
