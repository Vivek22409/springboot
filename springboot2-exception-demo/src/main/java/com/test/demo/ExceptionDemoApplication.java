package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.test.controller","com.test.exception"})
public class ExceptionDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExceptionDemoApplication.class, args);
	}
}
